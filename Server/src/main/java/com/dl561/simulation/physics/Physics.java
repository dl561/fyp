package com.dl561.simulation.physics;

import com.dl561.simulation.Simulation;
import com.dl561.simulation.Tick;
import com.dl561.simulation.course.location.Location;
import com.dl561.simulation.vehicle.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class Physics {

    public void calculateNewPositions(Simulation simulation) {
        System.out.println("Calculating new positions");
        for (Vehicle vehicle : simulation.getVehicles()) {
            updateVehicle(vehicle);
        }
    }

    private void updateVehicle(Vehicle vehicle) {
        Vector2D totalForce = calculateTotalForce(vehicle);

        Vector2D acceleration = totalForce.divideByConstant(vehicle.getMass());

        Vector2D oldVelocity = Vector2D.getByXAndY(vehicle.getxVelocity(), vehicle.getyVelocity());
        Vector2D accelerationWithTime = acceleration.multipleByConstant(Tick.TICK_TIME);
        Vector2D newVelocity = oldVelocity.add(accelerationWithTime);

        Vector2D oldPosition = Vector2D.getByXAndY(vehicle.getLocation().getX(), vehicle.getLocation().getY());
        Vector2D velocityWithTime = newVelocity.multipleByConstant(Tick.TICK_TIME);
        Vector2D newPosition = oldPosition.add(velocityWithTime);

        vehicle.setxVelocity(newVelocity.getX());
        vehicle.setyVelocity(newVelocity.getY());
        vehicle.setLocation(new Location(newPosition.getX(), newPosition.getY()));
    }

    private Vector2D calculateTotalForce(Vehicle vehicle) {
        Vector2D forceEngine = calculateEngineForce(vehicle);
        Vector2D forceBraking = calculateBrakingForce(vehicle);
        Vector2D forceDrag = calculateDragForce(vehicle);
        Vector2D forceRollingResistance = calculateRollingResistanceForce(vehicle);
        //TODO: work out how to deal with braking and accelerating at the same time
        //I think I'm happy letting them cancel out
        return forceEngine.add(forceBraking).add(forceDrag).add(forceRollingResistance);
    }

    private Vector2D calculateEngineForce(Vehicle vehicle) {
        double magnitude = vehicle.getAcceleratorPedalDepth() / 100 * vehicle.getMaxEngineForce();
        double direction = Math.toRadians(vehicle.getDirectionOfTravel());
        return new Vector2D(direction, magnitude);
    }

    private Vector2D calculateBrakingForce(Vehicle vehicle) {
        double magnitude = vehicle.getBrakePedalDepth() / 100 * vehicle.getMaxBrakingForce() * -1;
        double direction = Math.toRadians(vehicle.getDirectionOfTravel());
        return new Vector2D(direction, magnitude);
    }

    private Vector2D calculateDragForce(Vehicle vehicle) {
        double xVelocity = vehicle.getxVelocity();
        double yVelocity = vehicle.getyVelocity();
        double speed = Math.sqrt((xVelocity * xVelocity) + (yVelocity * yVelocity));
        double xDrag = vehicle.getDragConstant() * xVelocity * speed;
        double yDrag = vehicle.getDragConstant() * yVelocity * speed;
        Vector2D dragForce = new Vector2D();
        dragForce.setXAndY(xDrag, yDrag);
        return dragForce;
    }

    private Vector2D calculateRollingResistanceForce(Vehicle vehicle) {
        double rrForceX = vehicle.getxVelocity() * vehicle.getRollingResistanceConstant();
        double rrForceY = vehicle.getyVelocity() * vehicle.getRollingResistanceConstant();
        Vector2D rollingResistanceForce = new Vector2D();
        rollingResistanceForce.setXAndY(rrForceX, rrForceY);
        return rollingResistanceForce;
    }

}
