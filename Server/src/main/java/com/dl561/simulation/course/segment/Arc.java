package com.dl561.simulation.course.segment;

public class Arc {

    private int x;
    private int y;
    private double radius;
    private double startAngle;
    private double endAngle;
    private boolean counterClockwise;
    private int rotation;
    private String hexColour;

    public Arc() {
    }

    public Arc(int x, int y, double radius, double startAngle, double endAngle, boolean counterClockwise, int rotation, String hexColour) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.startAngle = startAngle;
        this.endAngle = endAngle;
        this.counterClockwise = counterClockwise;
        this.rotation = rotation;
        this.hexColour = hexColour;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(double startAngle) {
        this.startAngle = startAngle;
    }

    public double getEndAngle() {
        return endAngle;
    }

    public void setEndAngle(double endAngle) {
        this.endAngle = endAngle;
    }

    public boolean isCounterClockwise() {
        return counterClockwise;
    }

    public void setCounterClockwise(boolean counterClockwise) {
        this.counterClockwise = counterClockwise;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }
}
