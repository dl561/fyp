package com.dl561.simulation.course;

import com.dl561.simulation.physics.Vector2D;

public class Waypoint {
    private Vector2D position;

    public static final double WAYPOINT_DISTANCE = 75;

    public double findDistanceToWaypoint(Vector2D startPoint) {
        double xDiff = position.getX() - startPoint.getX();
        double yDiff = position.getY() - startPoint.getY();
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    public Waypoint(Vector2D position) {
        this.position = position;
    }

    public Waypoint(double x, double y) {
        position = new Vector2D(x, y);
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }
}
