package com.dl561.simulation.physics;

import com.dl561.simulation.vehicle.Vertex;

public class CollisionData {
    private boolean collisionStatus = false;
    private CollisionType collisionType;
    private Collidable a;
    private Collidable b;
    private Edge sideNormal;
    private Vertex collidingCorner;
    private Vector2D velocityOfCorner;

    public boolean isCollisionStatus() {
        return collisionStatus;
    }

    public void setCollisionStatus(boolean collisionStatus) {
        this.collisionStatus = collisionStatus;
    }

    public Collidable getA() {
        return a;
    }

    public void setA(Collidable a) {
        this.a = a;
    }

    public Collidable getB() {
        return b;
    }

    public void setB(Collidable b) {
        this.b = b;
    }

    public Edge getSideNormal() {
        return sideNormal;
    }

    public void setSideNormal(Edge sideNormal) {
        this.sideNormal = sideNormal;
    }

    public Vertex getCollidingCorner() {
        return collidingCorner;
    }

    public void setCollidingCorner(Vertex collidingCorner) {
        this.collidingCorner = collidingCorner;
    }

    public Vector2D getVelocityOfCorner() {
        return velocityOfCorner;
    }

    public void setVelocityOfCorner(Vector2D velocityOfCorner) {
        this.velocityOfCorner = velocityOfCorner;
    }

    public CollisionType getCollisionType() {
        return collisionType;
    }

    public void setCollisionType(CollisionType collisionType) {
        this.collisionType = collisionType;
    }
}
