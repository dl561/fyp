package com.dl561.simulation.course.segment;

import com.dl561.simulation.physics.Collidable;
import com.dl561.simulation.physics.Vector2D;

public class Rectangle extends Collidable {

    private int id;
    private double x;
    private double y;
    private double width;
    private double length;
    private double rotation;
    private String hexColour;
    private boolean isSolid;

    public Rectangle(int id, double x, double y, double width, double length, double rotation, String hexColour, boolean isSolid) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.length = length;
        this.rotation = rotation;
        this.hexColour = hexColour;
        this.isSolid = isSolid;
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

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public double getRotation() {
        return rotation;
    }

    @Override
    protected Vector2D getVelocity() {
        return new Vector2D(0d, 0d);
    }

    @Override
    protected double getAngularVelocity() {
        return 0d;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public String getHexColour() {
        return hexColour;
    }

    public void setHexColour(String hexColour) {
        this.hexColour = hexColour;
    }

    @Override
    protected boolean isMovable() {
        return false;
    }

    @Override
    public boolean isSolid() {
        return isSolid;
    }

    @Override
    protected int getId() {
        return id;
    }

    @Override
    protected Vector2D getCentreMass() {
        System.out.println("This is saying that the wall has moved into the car!!!");
        return null;
    }

    @Override
    protected double getCoefficientOfResitution() {
        System.out.println("This is saying that the wall has moved into the car!!!");
        return 0;
    }

    @Override
    protected double getMass() {
        System.out.println("This is saying that the wall has moved into the car!!!");
        return 0;
    }

    @Override
    protected double getInertia() {
        System.out.println("This is saying that the wall has moved into the car!!!");
        return 0;
    }

    @Override
    protected void applyCollision(Vector2D velocityAddition, double angularVelocityAddition) {
        System.out.println("This should not be moving!");
    }

    @Override
    protected void applyCollision(Vector2D velocityAddition) {
        System.out.println("This should not be moving");
    }

    @Override
    protected void nudge(Vector2D normal) {
        System.out.println("This should not be moving!!");
    }
}
