package com.dl561.simulation.physics;

public class Edge {

    private double length;
    private Vector2D normal;

    public Edge(double length, Vector2D normal) {
        this.length = length;
        this.normal = normal;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public Vector2D getNormal() {
        return normal;
    }

    public void setNormal(Vector2D normal) {
        this.normal = normal;
    }
}
