package com.dl561.simulation.physics;

import com.dl561.simulation.Simulation;
import com.dl561.simulation.Tick;
import com.dl561.simulation.computer.AIService;
import com.dl561.simulation.course.location.Location;
import com.dl561.simulation.vehicle.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Physics {

    @Value("${physics.message.debug}")
    private boolean showDebug;
    @Value("${physics.message.summary}")
    private boolean showSummary;
    private static final double precision = 0.1d;
    private static final double GRAVITY = 9.81d;
    final double CA_R = -5.20;   /* cornering stiffness */
    final double CA_F = -5.0;    /* cornering stiffness */
    final double MAX_GRIP = 2.0;

    private final AIService aiService;

    @Autowired
    public Physics(AIService aiService) {
        this.aiService = aiService;
    }

    public void calculateNewPositions(Simulation simulation) {
        if (showDebug) {
            System.out.println("Calculating new positions");
        }
        for (Vehicle vehicle : simulation.getVehicles()) {
            updateVehicle(simulation, vehicle);
        }
    }

    public void updateVehicle(Simulation simulation, Vehicle vehicle) {
        if (vehicle.isComputer()) {
            aiService.getComputerInput(simulation, vehicle);
        }
        calculateVehiclePhysics(vehicle);
    }

    private void calculateVehiclePhysics(Vehicle vehicle) {
        Vector2D velocity = vehicle.getVehicleReferenceVelocity();

        double rotationAngle = 0;
        double sideSlip = 0;
        if (normalise(velocity.getX(), precision) != 0) {
            double yawSpeed = 0.5 * vehicle.getWheelBaseConstant() * vehicle.getAngularVelocity();
            rotationAngle = Math.atan(yawSpeed / velocity.getX());
            sideSlip = Math.atan(velocity.getY() / velocity.getX());
        } else {
            vehicle.setAngularVelocity(0);
        }


        double slipAngleFront = sideSlip + rotationAngle - (vehicle.getSteeringWheelDirection() / 20);
        double slipAngleRear = sideSlip - rotationAngle;

        double frontWheelWeight = calculateWheelWeight(vehicle, true);
        double rearWheelWeight = calculateWheelWeight(vehicle, false);

        Vector2D frontWheelLateralForce = calculateLateralForce(CA_F, slipAngleFront, frontWheelWeight, vehicle, true);
        Vector2D rearWheelLateralForce = calculateLateralForce(CA_R, slipAngleRear, rearWheelWeight, vehicle, false);


        Vector2D tractionForce = calculateTractionForce(vehicle);

        Vector2D rollingResistance = calculateRollingResistanceForce(vehicle.getRollingResistanceConstant(), velocity);
        Vector2D dragResistance = calculateDragForce(vehicle.getDragConstant(), velocity);

        Vector2D resistance = new Vector2D();
        resistance.setX(rollingResistance.getX() + dragResistance.getX());
        resistance.setY(rollingResistance.getY() + dragResistance.getY());

        Vector2D totalForce = new Vector2D();
        totalForce.setX(tractionForce.getX() + frontWheelLateralForce.getX() + rearWheelLateralForce.getX() + resistance.getX());
        totalForce.setY(tractionForce.getY() + frontWheelLateralForce.getY() + rearWheelLateralForce.getY() + resistance.getY());

        double torque = calculateTorque(frontWheelLateralForce, rearWheelLateralForce, vehicle);


        Vector2D acceleration = new Vector2D();
        acceleration.setX(totalForce.getX() / vehicle.getMass());
        acceleration.setY(totalForce.getY() / vehicle.getMass());
        double angularAcceleration = torque / vehicle.getInertia();

        acceleration.setX(normalise(acceleration.getX(), precision));
        acceleration.setY(normalise(acceleration.getY(), precision));

        Vector2D worldReferenceAcceleration = new Vector2D();
        worldReferenceAcceleration.setX(vehicle.getCos() * acceleration.getY() + vehicle.getSin() * acceleration.getX());
        worldReferenceAcceleration.setY(-vehicle.getSin() * acceleration.getY() + vehicle.getCos() * acceleration.getX());

        Vector2D worldReferenceVelocity = new Vector2D();
        worldReferenceVelocity.setX(vehicle.getWRXVelocity() + (Tick.SECONDS_PER_TICK * worldReferenceAcceleration.getX()));
        worldReferenceVelocity.setY(vehicle.getWRYVelocity() + (Tick.SECONDS_PER_TICK * worldReferenceAcceleration.getY()));

        Vector2D newPosition = new Vector2D();
        newPosition.setX(Tick.SECONDS_PER_TICK * -worldReferenceVelocity.getX() + vehicle.getLocation().getX());
        newPosition.setY(Tick.SECONDS_PER_TICK * worldReferenceVelocity.getY() + vehicle.getLocation().getY());

        vehicle.setWRXVelocity(normalise(worldReferenceVelocity.getX(), precision));
        vehicle.setWRYVelocity(normalise(worldReferenceVelocity.getY(), precision));

        if (vehicle.getWRXVelocity() == 0 && vehicle.getWRYVelocity() == 0) {
            vehicle.setAngularVelocity(0);
        } else {
            vehicle.setAngularVelocity(vehicle.getAngularVelocity() + (Tick.SECONDS_PER_TICK * angularAcceleration));
        }

        vehicle.setDirectionOfTravel(vehicle.getDirectionOfTravel() + Tick.SECONDS_PER_TICK * vehicle.getAngularVelocity());
        vehicle.setLocation(new Location(newPosition.getX(), newPosition.getY()));
        vehicle.setTorque(torque);
        vehicle.setVehicleReferenceXAcceleration(acceleration.getX());
        vehicle.setVehicleReferenceYAcceleration(acceleration.getY());
        vehicle.setWorldReferenceXAcceleration(worldReferenceAcceleration.getX());
        vehicle.setWorldReferenceYAcceleration(worldReferenceAcceleration.getY());
    }

    /**
     * Calculating the torque on the vehicle body from the lateral forces
     *
     * @param frontWheelLateralForce
     * @param rearWheelLateralForce
     * @param vehicle
     * @return
     */
    private double calculateTorque(Vector2D frontWheelLateralForce, Vector2D rearWheelLateralForce, Vehicle vehicle) {
        double frontTorque = frontWheelLateralForce.getY() * vehicle.getFrontWheelBase();
        double rearTorque = rearWheelLateralForce.getY() * vehicle.getRearWheelBase();
        return frontTorque - rearTorque;
    }

    private double calculateWheelWeight(Vehicle vehicle, boolean front) {
        double stationaryWheelWeight = 0.5 * vehicle.getWeight();
        double centreOfGravity = vehicle.getCentreOfGravityHeight() / vehicle.getWheelBaseConstant();
        double centreOfGravityWithMassAndAcceleration = centreOfGravity * vehicle.getMass() * vehicle.getVehicleReferenceXAcceleration();
        double wheelWeight = stationaryWheelWeight;
        if (front) {
            wheelWeight -= centreOfGravityWithMassAndAcceleration;
        } else {
            wheelWeight += centreOfGravityWithMassAndAcceleration;
        }
        return wheelWeight;
    }

    public Vector2D calculateTractionForce(Vehicle vehicle) {
        Vector2D tractionForce = new Vector2D();
        tractionForce.setX(calculateXEngineForce(vehicle));
        tractionForce.setY(0);

        if (vehicle.isRearSlip()) {
            //TODO: change this 0.5d based on the surface
            tractionForce.setX(tractionForce.getX() * 0.5d);
        }
        return tractionForce;
    }

    public double calculateXEngineForce(Vehicle vehicle) {
        double force = vehicle.getAcceleratorPedalDepth() * vehicle.getMaxEngineForce() * vehicle.getGearRatio(vehicle.getGear());
        if (normalise(vehicle.getVehicleReferenceXAcceleration(), precision) > 0) {
            force -= vehicle.getBrakePedalDepth() * vehicle.getMaxBrakingForce();
        }
        return force;
    }

    public Vector2D calculateRollingResistanceForce(double rollingResistanceConstant, Vector2D vehicleReferenceVelocity) {
        Vector2D rollingResistance = new Vector2D();
        rollingResistance.setX(-rollingResistanceConstant * vehicleReferenceVelocity.getX());
        rollingResistance.setY(-rollingResistanceConstant * vehicleReferenceVelocity.getY());
        return rollingResistance;
    }

    public Vector2D calculateDragForce(double dragConstant, Vector2D vehicleReferenceVelocity) {
        Vector2D drag = new Vector2D();
        drag.setX(-dragConstant * vehicleReferenceVelocity.getX() * Math.abs(vehicleReferenceVelocity.getX()));
        drag.setY(-dragConstant * vehicleReferenceVelocity.getY() * Math.abs(vehicleReferenceVelocity.getY()));
        return drag;
    }

    private Vector2D calculateLateralForce(double corneringStiffness, double slipAngle, double wheelWeight, Vehicle vehicle, boolean isFront) {
        boolean slip = (isFront) ? vehicle.isFrontSlip() : vehicle.isRearSlip();
        Vector2D lateralForce = new Vector2D();
        lateralForce.setX(0);
        lateralForce.setY(normalise(-MAX_GRIP, MAX_GRIP, corneringStiffness * slipAngle));
        lateralForce.setY(lateralForce.getY() * wheelWeight);

        if (slip) {
            //TODO: change this 0.5d to a variable based on the surface
            lateralForce.setY(lateralForce.getY() * 0.5d);
        }

        if (isFront) {
            lateralForce.setX(Math.sin(vehicle.getSteeringWheelDirection()) * lateralForce.getX());
            lateralForce.setY(Math.cos(vehicle.getSteeringWheelDirection()) * lateralForce.getY());
        }
        return lateralForce;
    }

    public static double normalise(double min, double max, double value) {
        if (value >= min && value <= max) {
            return value;
        } else if (value <= min) {
            return min;
        } else if (value >= max) {
            return max;
        } else {
            System.out.println("Normalisation failed!?!?! for " + min + " < " + value + " < " + max);
            return value;
        }
    }

    public static double normalise(double value, double precision) {
        if (value > 0 && value < precision) {
            return 0;
        } else if (value < 0 && value > -precision) {
            return 0;
        }
        return value;
    }
}
