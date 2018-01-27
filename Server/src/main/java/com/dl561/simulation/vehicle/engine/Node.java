package com.dl561.simulation.vehicle.engine;

public class Node {
    private int x;
    private int y;

    public int distanceToPoint(int x1) {
        return Math.abs(x1 - x);
    }

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
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
}
