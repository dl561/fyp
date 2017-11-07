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

    public void calculateNewPositions(Simulation simulation) {
        if (showDebug) {
            System.out.println("Calculating new positions");
        }
        for (Vehicle vehicle : simulation.getVehicles()) {
            updateVehicle(vehicle);
        }
    }

    public void updateVehicle(Vehicle vehicle) {
        Vector2D forwardForce = calculateForwardForce(vehicle);
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

}
