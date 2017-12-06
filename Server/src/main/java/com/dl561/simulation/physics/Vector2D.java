package com.dl561.simulation.physics;

public class Vector2D {

    private double direction;
    private double magnitude;

    public Vector2D() {
    }

    public Vector2D(double direction, double magnitude) {
        this.direction = direction;
        this.magnitude = magnitude;
    }

    public static Vector2D getByXAndY(double x, double y) {
        Vector2D newVector = new Vector2D();
        newVector.setXAndY(x, y);
        return newVector;
    }

    public Vector2D add(Vector2D other) {
        Vector2D newVector = new Vector2D();
        double x = this.getX() + other.getX();
        double y = this.getY() + other.getY();
        newVector.setXAndY(x, y);
        return newVector;
    }

    public Vector2D subtract(Vector2D other) {
        Vector2D newVector = new Vector2D();
        double x = this.getX() - other.getX();
        double y = this.getY() - other.getY();
        newVector.setXAndY(x, y);
        return newVector;
    }

    public Vector2D divideByConstant(double constant) {
        Vector2D newVector = new Vector2D();
        newVector.setDirection(this.direction);
        newVector.setMagnitude(this.magnitude / constant);
        return newVector;
    }

    public Vector2D multipleByConstant(double constant) {
        Vector2D newVector = new Vector2D();
        newVector.setDirection(this.direction);
        newVector.setMagnitude(this.magnitude * constant);
        return newVector;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public double getX() {
        return Math.cos(direction) * magnitude;
    }

    public double getY() {
        return Math.sin(direction) * magnitude;
    }

    public void setXAndY(double x, double y) {
        if (x == -0.0d) {
            x = 0d;
        }
        if (y == -0.0d) {
            y = 0d;
        }
        this.direction = normaliseRadians(Math.atan2(y, x));
        this.magnitude = Math.sqrt((x * x) + (y * y));
    }

    private double normaliseRadians(double radians) {
        double degrees = Math.toDegrees(radians);
        degrees = degrees % 360;

        if (degrees < 0) {
            degrees = 360 + degrees;
        }
        return Math.toRadians(degrees);
    }

    public void setX(double x) {
        double y = getY();
        setXAndY(x, y);
    }

    public void setY(double y) {
        double x = getX();
        setXAndY(x, y);
    }
}
