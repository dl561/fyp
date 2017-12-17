package com.dl561.simulation.physics;

public class Vector2D {

    private double x;
    private double y;

    public Vector2D() {
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }


    public double getMagnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public double dotProduct(Vector2D that) {
        return this.x * that.x + this.y + that.y;
    }
}
