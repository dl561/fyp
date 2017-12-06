package com.dl561.simulation.physics;

import com.dl561.simulation.Simulation;
import com.dl561.simulation.Tick;
import com.dl561.simulation.course.location.Location;
import com.dl561.simulation.vehicle.Vehicle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Physics {

    @Value("${physics.message.debug}")
    private boolean showDebug;
    @Value("${physics.message.summary}")
    private boolean showSummary;
    private static final double precision = 0.1d;

    public void calculateNewPositions(Simulation simulation) {
        if (showDebug) {
            System.out.println("Calculating new positions");
        }
        for (Vehicle vehicle : simulation.getVehicles()) {
            updateVehicle(vehicle);
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

    private void cheat(Vehicle vehicle) {
        final double CA_R = -5.20;   /* cornering stiffness */
        final double CA_F = -5.0;    /* cornering stiffness */
        final double MAX_GRIP = 2.0;
        //TODO: change these to show slipping
        double frontSlip = 0;
        double rearSlip = 0;

        Vector2DNoMAGDIR velocity = vehicle.getVehicleReferenceVelocity();

        double yawSpeed = 0.5 * vehicle.getWheelBaseConstant() * vehicle.getAngularVelocity();

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

        double slipAngleFront = sideSlip + rotationAngle - vehicle.getSteerAngleInRadians();
        double slipAngleRear = sideSlip - rotationAngle;

        // weight per axle = half car mass times 1G (=9.8m/s^2)
        double weight = vehicle.getMass() * 9.8 * 0.5;

        Vector2DNoMAGDIR frontWheelLateralForce = new Vector2DNoMAGDIR();
        frontWheelLateralForce.setX(0);
        frontWheelLateralForce.setY(CA_F * slipAngleFront);
        frontWheelLateralForce.setY(Math.min(MAX_GRIP, frontWheelLateralForce.getY()));
        frontWheelLateralForce.setY(Math.max(-MAX_GRIP, frontWheelLateralForce.getY()));
        frontWheelLateralForce.setY(frontWheelLateralForce.getY() * weight);

        if (frontSlip == 1) {
            frontWheelLateralForce.setY(frontWheelLateralForce.getY() * 0.5d);
        }

        Vector2DNoMAGDIR rearWheelLateralForce = new Vector2DNoMAGDIR();
        rearWheelLateralForce.setX(0);
        rearWheelLateralForce.setY(CA_R * slipAngleRear);
        rearWheelLateralForce.setY(Math.min(MAX_GRIP, rearWheelLateralForce.getY()));
        rearWheelLateralForce.setY(Math.max(-MAX_GRIP, rearWheelLateralForce.getY()));
        rearWheelLateralForce.setY(rearWheelLateralForce.getY() * weight);

        if (rearSlip == 1) {
            rearWheelLateralForce.setY(rearWheelLateralForce.getY() * 0.5d);
        }

        Vector2DNoMAGDIR tractionForce = new Vector2DNoMAGDIR();
        tractionForce.setX(calculateXEngineForce(vehicle));
        tractionForce.setY(0);

        if (rearSlip == 1) {
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
        double frontWheelLateralX = Math.sin(vehicle.getSteerAngleInRadians()) * frontWheelLateralForce.getX();
        double rearWheelLateralX = rearWheelLateralForce.getX();
        double frontWheelLateralY = Math.cos(vehicle.getSteerAngleInRadians()) * frontWheelLateralForce.getY();
        double rearWheelLateralY = rearWheelLateralForce.getY();

        totalForce.setX(tractionForce.getX() + frontWheelLateralX + rearWheelLateralX + resistance.getX());
        totalForce.setY(tractionForce.getY() + frontWheelLateralY + rearWheelLateralY + resistance.getY());

        // torque on body from lateral forces
        //TODO: need to implement a B and C variable for the vehicle, for the distance from CG to front/rear axle
        double frontTorque = frontWheelLateralForce.getY() * vehicle.getFrontWheelBase();
        double rearTorque = rearWheelLateralForce.getY() * vehicle.getRearWheelBase();
        double torque = frontTorque - rearTorque;

// Acceleration

        Vector2DNoMAGDIR acceleration = new Vector2DNoMAGDIR();
        acceleration.setX(totalForce.getX() / vehicle.getMass());
        acceleration.setY(totalForce.getY() / vehicle.getMass());
        // Newton F = m.a, therefore a = F/m
        //TODO: add inertia to the vehicle
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
        worldReferenceVelocity.setX(vehicle.getXVelocity() + (Tick.SECONDS_PER_TICK * worldReferenceAcceleration.getX()));
        worldReferenceVelocity.setY(vehicle.getYVelocity() + (Tick.SECONDS_PER_TICK * worldReferenceAcceleration.getY()));

        // position is integrated velocity
        Vector2DNoMAGDIR newPosition = new Vector2DNoMAGDIR();
        newPosition.setX(Tick.SECONDS_PER_TICK * worldReferenceVelocity.getX() + vehicle.getLocation().getX());
        newPosition.setY(Tick.SECONDS_PER_TICK * worldReferenceVelocity.getY() + vehicle.getLocation().getY());

        vehicle.setXVelocity(normalise(worldReferenceVelocity.getX(), precision));
        vehicle.setYVelocity(normalise(worldReferenceVelocity.getY(), precision));

        if (vehicle.getXVelocity() == 0 && vehicle.getYVelocity() == 0) {
            vehicle.setAngularVelocity(0);
        } else {
            vehicle.setAngularVelocity(vehicle.getAngularVelocity() + (Tick.SECONDS_PER_TICK * angularAcceleration));
        }

        vehicle.setDirectionOfTravel(vehicle.getDirectionOfTravel() + Math.toDegrees(Tick.SECONDS_PER_TICK * vehicle.getAngularVelocity()));
        vehicle.setLocation(new Location(newPosition.getX(), newPosition.getY()));
    }

    public void updateVehicle(Vehicle vehicle) {
        cheat(vehicle);

        /**   Vector2D forwardForce = calculateForwardForce(vehicle);
         Vector2D backwardForce = calculateBackwardForce(vehicle);

         Vector2D totalForce = forwardForce.add(backwardForce);

         Vector2D acceleration = totalForce.divideByConstant(vehicle.getMass());

         double accelerationForWheelWeight;
         if (forwardForce.getMagnitude() > backwardForce.getMagnitude()) {
         accelerationForWheelWeight = acceleration.getMagnitude();
         } else if (forwardForce.getMagnitude() < backwardForce.getMagnitude()) {
         accelerationForWheelWeight = -acceleration.getMagnitude();
         } else {
         accelerationForWheelWeight = acceleration.getMagnitude();
         }

         System.out.println("Acceleration: " + accelerationForWheelWeight);

         double frontWheelWeight = calculateWheelWeight(vehicle, accelerationForWheelWeight, true);
         double rearWheelWeight = calculateWheelWeight(vehicle, accelerationForWheelWeight, false);

         System.out.println("Front Wheel Weight:" + frontWheelWeight);
         System.out.println("Rear Wheel Weight:" + rearWheelWeight);

         Vector2D oldVelocity = Vector2D.getByXAndY(vehicle.getXVelocity(), vehicle.getYVelocity());
         Vector2D accelerationWithTime = acceleration.multipleByConstant(Tick.TICK_TIME);
         Vector2D newVelocity = oldVelocity.add(accelerationWithTime);

         Vector2D oldPosition = Vector2D.getByXAndY(vehicle.getLocation().getX(), vehicle.getLocation().getY());
         Vector2D velocityWithTime = newVelocity.multipleByConstant(Tick.TICK_TIME);
         Vector2D newPosition = oldPosition.add(velocityWithTime);

         vehicle.setXVelocity(newVelocity.getX());
         vehicle.setYVelocity(newVelocity.getY());
         vehicle.setLocation(new Location(newPosition.getX(), newPosition.getY()));
         **/
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
        Vector2D velocity = new Vector2D(vehicle.getXVelocity(), vehicle.getYVelocity());
        if (velocity.getMagnitude() < 0.000001d || velocity.getMagnitude() > -0.0000001d) {
            //Cannot brake past 0m/s
            //Without this you can accelerate backwards with the brake (this might be something useful)
            return new Vector2D(direction, 0d);
        }
        double magnitude = vehicle.getBrakePedalDepth() / 100 * vehicle.getMaxBrakingForce();
        return new Vector2D(direction, magnitude);
    }

    public Vector2D calculateDragForce(Vehicle vehicle) {
        double xVelocity = vehicle.getXVelocity();
        double yVelocity = vehicle.getYVelocity();
        double speed = Math.sqrt((xVelocity * xVelocity) + (yVelocity * yVelocity));
        double xDrag = vehicle.getDragConstant() * xVelocity * speed * -1;
        double yDrag = vehicle.getDragConstant() * yVelocity * speed * -1;
        Vector2D dragForce = new Vector2D();
        dragForce.setXAndY(xDrag, yDrag);
        return dragForce;
    }

    public Vector2D calculateRollingResistanceForce(Vehicle vehicle) {
        double rrForceX = vehicle.getXVelocity() * vehicle.getRollingResistanceConstant() * -1;
        double rrForceY = vehicle.getYVelocity() * vehicle.getRollingResistanceConstant() * -1;
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
