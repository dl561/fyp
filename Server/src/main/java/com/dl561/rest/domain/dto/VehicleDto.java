package com.dl561.rest.domain.dto;

import com.dl561.simulation.physics.Vector2D;

public class VehicleDto {
    private int id;
    private Vector2D location;
    private double directionOfTravel;
    private double steeringWheelDirection;
    private double acceleratorPedalDepth;
    private double brakePedalDepth;
    private int gear;
    private boolean isComputer = false;
    private int waypointNumber = 0;
    private int lap = 0;
    private boolean finished = false;
    private int position = id + 1;
    private int rpm = 1000;
    private double speed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vector2D getLocation() {
        return location;
    }

    public void setLocation(Vector2D location) {
        this.location = location;
    }

    public double getDirectionOfTravel() {
        return directionOfTravel;
    }

    public void setDirectionOfTravel(double directionOfTravel) {
        this.directionOfTravel = directionOfTravel;
    }

    public double getSteeringWheelDirection() {
        return steeringWheelDirection;
    }

    public void setSteeringWheelDirection(double steeringWheelDirection) {
        this.steeringWheelDirection = steeringWheelDirection;
    }

    public double getAcceleratorPedalDepth() {
        return acceleratorPedalDepth;
    }

    public void setAcceleratorPedalDepth(double acceleratorPedalDepth) {
        this.acceleratorPedalDepth = acceleratorPedalDepth;
    }

    public double getBrakePedalDepth() {
        return brakePedalDepth;
    }

    public void setBrakePedalDepth(double brakePedalDepth) {
        this.brakePedalDepth = brakePedalDepth;
    }

    public int getGear() {
        return gear;
    }

    public void setGear(int gear) {
        this.gear = gear;
    }

    public boolean isComputer() {
        return isComputer;
    }

    public void setComputer(boolean computer) {
        isComputer = computer;
    }

    public int getWaypointNumber() {
        return waypointNumber;
    }

    public void setWaypointNumber(int waypointNumber) {
        this.waypointNumber = waypointNumber;
    }

    public int getLap() {
        return lap;
    }

    public void setLap(int lap) {
        this.lap = lap;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getRpm() {
        return rpm;
    }

    public void setRpm(int rpm) {
        this.rpm = rpm;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
