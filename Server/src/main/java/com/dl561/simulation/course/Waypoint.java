package com.dl561.simulation.course;

import com.dl561.simulation.physics.Physics;
import com.dl561.simulation.physics.Vector2D;

public class Waypoint {
    private Vector2D position;

    public static final double WAYPOINT_DISTANCE = 75;

    /**
     * Gives the distance to this from the Vector2D inputted.
     *
     * @param startPoint the other point to get the distance to
     * @return double   The distance from this to the inputted Vector2D
     */
    public double findDistanceToWaypoint(Vector2D startPoint) {
        double xDiff = position.getX() - startPoint.getX();
        double yDiff = position.getY() - startPoint.getY();
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    public double findBearingToWaypoint(Vector2D startPoint) {
        double angle = Math.atan2(startPoint.getX() - position.getX(), startPoint.getY() - position.getY());
        angle = Physics.normaliseBearing(angle);
        return angle;
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
