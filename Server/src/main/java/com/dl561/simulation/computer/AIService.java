package com.dl561.simulation.computer;

import com.dl561.simulation.Simulation;
import com.dl561.simulation.course.Waypoint;
import com.dl561.simulation.vehicle.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class AIService {

    public void getComputerInput(Simulation simulation, Vehicle vehicle) {
        double angleToNextWaypoint = findAngleToNextWaypoint(simulation, vehicle);
        System.out.println("Bearing to waypoint " + (vehicle.getWaypointNumber() + 1) + ": " + Math.toDegrees(angleToNextWaypoint) + ", vehicle direction: " + Math.toDegrees(vehicle.getDirectionOfTravel()));
        String newSteerDirection = findSteerDirection(vehicle.getDirectionOfTravel(), vehicle.getOppositeDirectionOfTravel(), angleToNextWaypoint);
        switch (newSteerDirection) {
            case "LEFT":
                vehicle.steerLeft();
                break;
            case "RIGHT":
                vehicle.steerRight();
                break;
            case "NONE":
                //The car is unlikely to do this, it'll wiggle rather than go straight
                vehicle.normaliseSteerAngle();
                break;
        }
        vehicle.setAcceleratorPedalDepth(100);
    }

    private int getNextWaypointNumber(Simulation simulation, Vehicle vehicle) {
        return vehicle.getWaypointNumber() + 1 < simulation.getWaypoints().length ? vehicle.getWaypointNumber() + 1 : 0;
    }

    private String findSteerDirection(double vehicleDirection, double vehicleOppositeDirection, double angleToNextWaypoint) {
        //TODO: dot product with the left normal and the right normal to find left or right turn
        double rightLowerBound = vehicleDirection;
        double rightUpperBound = vehicleOppositeDirection;

        double leftLowerBound = vehicleOppositeDirection;
        double leftUpperBound = vehicleDirection;

        if (cyclicBetween(rightLowerBound, rightUpperBound, angleToNextWaypoint)) {
            System.out.println("RIGHT");
            return "RIGHT";
        } else if (cyclicBetween(leftLowerBound, leftUpperBound, angleToNextWaypoint)) {
            System.out.println("LEFT");
            return "LEFT";
        } else {
            System.out.println("NONE");
            return "NONE";
        }
    }

    /**
     * For use with bearings, finding the between even if the values cross the 0 line
     *
     * @param lower
     * @param upper
     * @param value
     * @return
     */
    private boolean cyclicBetween(double lower, double upper, double value) {
        //lower -> upper is always clockwise, if upper < lower then it has gone through 0
        if (upper < lower) {
            upper += (2 * Math.PI);
        }
        System.out.println(Math.toDegrees(lower) + " < " + Math.toDegrees(value) + " < " + Math.toDegrees(upper));
        return lower < value && value < upper;
    }

    private double findAngleToNextWaypoint(Simulation simulation, Vehicle vehicle) {
        int nextWaypointNumber = vehicle.getWaypointNumber() + 1 < simulation.getWaypoints().length ? vehicle.getWaypointNumber() + 1 : 0;
        Waypoint nextWaypoint = simulation.getWaypoints()[nextWaypointNumber];
        return nextWaypoint.findBearingToWaypoint(vehicle.getLocation());
    }
}

