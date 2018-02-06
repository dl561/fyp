package com.dl561.simulation;

import com.dl561.simulation.vehicle.Vehicle;

public class PositionNode {
    Vehicle vehicle;
    int lap;
    int waypoint;
    double distanceToNextWaypoint;

    public PositionNode(Vehicle vehicle, int lap, int waypoint, double distanceToNextWaypoint) {
        this.vehicle = vehicle;
        this.lap = lap;
        this.waypoint = waypoint;
        this.distanceToNextWaypoint = distanceToNextWaypoint;
    }
}
