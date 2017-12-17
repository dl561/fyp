package com.dl561.simulation.vehicle;

import com.dl561.simulation.physics.Vector2D;

public class Vertex {

    private int id;
    private double x;
    private double y;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Vector2D asVector2D() {
        return new Vector2D(x, y);
    }
}
