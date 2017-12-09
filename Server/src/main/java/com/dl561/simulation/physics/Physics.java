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

    @Autowired
    private AIService aiService;

    public void calculateNewPositions(Simulation simulation) {
        if (showDebug) {
            System.out.println("Calculating new positions");
        }
        for (Vehicle vehicle : simulation.getVehicles()) {
            updateVehicle(simulation, vehicle);
        }
    }

    double SGN(double value) {
        if (value < 0.0) return -1.0;
        else return 1.0;
    }

    double ABS(double value) {
        if (value < 0.0) return -value;
        else return value;
    }

    //TODO: change this function name
    private void cheat(Vehicle vehicle) {
        final double CA_R = -5.20;   /* cornering stiffness */
        final double CA_F = -5.0;    /* cornering stiffness */
        final double MAX_GRIP = 2.0;

        Vector2DNoMAGDIR velocity = vehicle.getVehicleReferenceVelocity();

        double yawSpeed = 10 * vehicle.getWheelBaseConstant() * vehicle.getAngularVelocity();

        double rotationAngle = 0;
        double sideSlip = 0;
        if (velocity.getX() != 0) {
            //car is moving forwards
            rotationAngle = Math.atan(yawSpeed / velocity.getX());
        }

        if (velocity.getX() != 0) {
            sideSlip = Math.atan(velocity.getY() / velocity.getX());
        }

        if (velocity.getX() == 0) {
            vehicle.setAngularVelocity(0);
        }

        double slipAngleFront = sideSlip + rotationAngle - vehicle.getSteeringWheelDirection();
        double slipAngleRear = sideSlip - rotationAngle;

        // weight per axle = half car mass times 1G (=9.8m/s^2)
        double weight = vehicle.getMass() * GRAVITY * 0.5d;

        Vector2DNoMAGDIR frontWheelLateralForce = new Vector2DNoMAGDIR();
        frontWheelLateralForce.setX(0);
        frontWheelLateralForce.setY(normalise(-MAX_GRIP, MAX_GRIP, CA_F * slipAngleFront));
        frontWheelLateralForce.setY(frontWheelLateralForce.getY() * weight);

        if (vehicle.isFrontSlip()) {
            frontWheelLateralForce.setY(frontWheelLateralForce.getY() * 0.5d);
        }

        Vector2DNoMAGDIR rearWheelLateralForce = new Vector2DNoMAGDIR();
        rearWheelLateralForce.setX(0);
        rearWheelLateralForce.setY(normalise(-MAX_GRIP, MAX_GRIP, CA_R * slipAngleRear));
        rearWheelLateralForce.setY(rearWheelLateralForce.getY() * weight);

        if (vehicle.isRearSlip()) {
            rearWheelLateralForce.setY(rearWheelLateralForce.getY() * 0.5d);
        }

        Vector2DNoMAGDIR tractionForce = new Vector2DNoMAGDIR();
        tractionForce.setX(calculateXEngineForce(vehicle));
        tractionForce.setY(0);

        if (vehicle.isRearSlip()) {
            tractionForce.setX(tractionForce.getX() * 0.5d);
        }

        Vector2DNoMAGDIR resistance = new Vector2DNoMAGDIR();
        double rollingResistanceX = -vehicle.getRollingResistanceConstant() * velocity.getX();
        double rollingResistanceY = -vehicle.getRollingResistanceConstant() * velocity.getY();
        double dragResistanceX = -vehicle.getDragConstant() * velocity.getX() * ABS(velocity.getX());
        double dragResistanceY = -vehicle.getDragConstant() * velocity.getY() * ABS(velocity.getY());
        resistance.setX(rollingResistanceX + dragResistanceX);
        resistance.setY(rollingResistanceY + dragResistanceY);

        // sum forces
        Vector2DNoMAGDIR totalForce = new Vector2DNoMAGDIR();
        double frontWheelLateralX = Math.sin(vehicle.getSteeringWheelDirection()) * frontWheelLateralForce.getX();
        double rearWheelLateralX = rearWheelLateralForce.getX();
        double frontWheelLateralY = Math.cos(vehicle.getSteeringWheelDirection()) * frontWheelLateralForce.getY();
        double rearWheelLateralY = rearWheelLateralForce.getY();

        totalForce.setX(tractionForce.getX() + frontWheelLateralX + rearWheelLateralX + resistance.getX());
        totalForce.setY(tractionForce.getY() + frontWheelLateralY + rearWheelLateralY + resistance.getY());

        // torque on body from lateral forces
        double frontTorque = frontWheelLateralForce.getY() * vehicle.getFrontWheelBase();
        double rearTorque = rearWheelLateralForce.getY() * vehicle.getRearWheelBase();
        double torque = frontTorque - rearTorque;

// Acceleration

        Vector2DNoMAGDIR acceleration = new Vector2DNoMAGDIR();
        acceleration.setX(totalForce.getX() / vehicle.getMass());
        acceleration.setY(totalForce.getY() / vehicle.getMass());
        // Newton F = m.a, therefore a = F/m
        double angularAcceleration = torque / vehicle.getInertia();

        acceleration.setX(normalise(acceleration.getX(), precision));
        acceleration.setY(normalise(acceleration.getY(), precision));
// Velocity and position

        // transform acceleration from car reference frame to world reference frame
        Vector2DNoMAGDIR worldReferenceAcceleration = new Vector2DNoMAGDIR();
        worldReferenceAcceleration.setX(vehicle.getCos() * acceleration.getY() + vehicle.getSin() * acceleration.getX());
        worldReferenceAcceleration.setY(-vehicle.getSin() * acceleration.getY() + vehicle.getCos() * acceleration.getX());

        // velocity is integrated acceleration
        Vector2DNoMAGDIR worldReferenceVelocity = new Vector2DNoMAGDIR();
        worldReferenceVelocity.setX(vehicle.getWRXVelocity() + (Tick.SECONDS_PER_TICK * worldReferenceAcceleration.getX()));
        worldReferenceVelocity.setY(vehicle.getWRYVelocity() + (Tick.SECONDS_PER_TICK * worldReferenceAcceleration.getY()));

        // position is integrated velocity
        Vector2DNoMAGDIR newPosition = new Vector2DNoMAGDIR();
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

    public void updateVehicle(Simulation simulation, Vehicle vehicle) {
        if (vehicle.isComputer()) {
            aiService.getComputerInput(simulation, vehicle);
        }
        cheat(vehicle);
    }

    private double calculateWheelWeight(Vehicle vehicle, double accelerationValue, boolean front) {
        double stationaryWheelWeight = 0.5 * vehicle.getWeight();
        double centreOfGravity = vehicle.getCentreOfGravityHeight() / vehicle.getWheelBaseConstant();
        double centreOfGravityWithMassAndAcceleration = centreOfGravity * vehicle.getMass() * accelerationValue;
        double wheelWeight = stationaryWheelWeight;
        if (front) {
            wheelWeight -= centreOfGravityWithMassAndAcceleration;
        } else {
            wheelWeight += centreOfGravityWithMassAndAcceleration;
        }
        return wheelWeight;
    }

    private Vector2D calculateForwardForce(Vehicle vehicle) {
        Vector2D forceEngine = calculateEngineForce(vehicle);
        return forceEngine;
    }

    private Vector2D calculateBackwardForce(Vehicle vehicle) {
        Vector2D forceBraking = calculateBrakingForce(vehicle);
        Vector2D forceDrag = calculateDragForce(vehicle);
        Vector2D forceRollingResistance = calculateRollingResistanceForce(vehicle);
        return forceBraking.add(forceDrag).add(forceRollingResistance);
    }

    public Vector2D calculateTotalForce(Vehicle vehicle) {
        Vector2D forceEngine = calculateEngineForce(vehicle);
        Vector2D forceBraking = calculateBrakingForce(vehicle);
        Vector2D forceDrag = calculateDragForce(vehicle);
        Vector2D forceRollingResistance = calculateRollingResistanceForce(vehicle);
        if (showDebug) {
            System.out.println("Vehicle " + vehicle.getId() + " engine force = " + forceEngine.getMagnitude() + " in direction " + forceEngine.getDirection());
            System.out.println("Vehicle " + vehicle.getId() + " braking force = " + forceBraking.getMagnitude() + " in direction " + forceBraking.getDirection());
            System.out.println("Vehicle " + vehicle.getId() + " drag force = " + forceDrag.getMagnitude() + " in direction " + forceDrag.getDirection());
            System.out.println("Vehicle " + vehicle.getId() + " rolling force = " + forceRollingResistance.getMagnitude() + " in direction " + forceRollingResistance.getDirection());
        }
        return forceEngine.add(forceBraking).add(forceDrag).add(forceRollingResistance);
    }

    public Vector2D calculateEngineForce(Vehicle vehicle) {
        double magnitude = vehicle.getAcceleratorPedalDepth() / 100 * vehicle.getMaxEngineForce();
        double direction = Math.toRadians(vehicle.getDirectionOfTravel());
        return new Vector2D(direction, magnitude);
    }

    public double calculateXEngineForce(Vehicle vehicle) {
        return vehicle.getAcceleratorPedalDepth() * vehicle.getMaxEngineForce() * vehicle.getGearRatio(vehicle.getGear());
    }

    public Vector2D calculateBrakingForce(Vehicle vehicle) {
        double direction = Math.toRadians(vehicle.getOppositeDirectionOfTravel());
        Vector2D velocity = new Vector2D(vehicle.getWRXVelocity(), vehicle.getWRYVelocity());
        if (velocity.getMagnitude() < 0.000001d || velocity.getMagnitude() > -0.0000001d) {
            //Cannot brake past 0m/s
            //Without this you can accelerate backwards with the brake (this might be something useful)
            return new Vector2D(direction, 0d);
        }
        double magnitude = vehicle.getBrakePedalDepth() / 100 * vehicle.getMaxBrakingForce();
        return new Vector2D(direction, magnitude);
    }

    public Vector2D calculateDragForce(Vehicle vehicle) {
        double xVelocity = vehicle.getWRXVelocity();
        double yVelocity = vehicle.getWRYVelocity();
        double speed = Math.sqrt((xVelocity * xVelocity) + (yVelocity * yVelocity));
        double xDrag = vehicle.getDragConstant() * xVelocity * speed * -1;
        double yDrag = vehicle.getDragConstant() * yVelocity * speed * -1;
        Vector2D dragForce = new Vector2D();
        dragForce.setXAndY(xDrag, yDrag);
        return dragForce;
    }

    public Vector2D calculateRollingResistanceForce(Vehicle vehicle) {
        double rrForceX = vehicle.getWRXVelocity() * vehicle.getRollingResistanceConstant() * -1;
        double rrForceY = vehicle.getWRYVelocity() * vehicle.getRollingResistanceConstant() * -1;
        Vector2D rollingResistanceForce = new Vector2D();
        rollingResistanceForce.setXAndY(rrForceX, rrForceY);
        return rollingResistanceForce;
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
