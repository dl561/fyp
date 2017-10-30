package com.dl561.simulation.vehicle;

import com.dl561.rest.domain.dto.VehicleUpdateDto;
import com.dl561.simulation.course.location.Location;
import org.springframework.stereotype.Component;

@Component
public abstract class Vehicle {
    private int id;
    private Location location;
    private double directionOfTravel;
    private double xVelocity;
    private double yVelocity;
    private double xAcceleration;
    private double yAcceleration;
    private double mass;
    private double steeringWheelDirection;
    private double acceleratorPedalDepth;
    private double brakePedalDepth;
    private int gear;
    private VehicleType vehicleType;
    private double maxEngineForce;
    private double maxBrakingForce;
    private double dragConstant;
    private double rollingResistanceConstant;

    public Vehicle update(VehicleUpdateDto vehicleUpdateDto) {
        this.steeringWheelDirection = vehicleUpdateDto.getSteeringWheelOrientation();
        this.acceleratorPedalDepth = vehicleUpdateDto.getAcceleratorPedalDepth();
        this.brakePedalDepth = vehicleUpdateDto.getBrakePedalDepth();
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public double getDirectionOfTravel() {
        return directionOfTravel;
    }

    public void setDirectionOfTravel(double directionOfTravel) {
        this.directionOfTravel = directionOfTravel;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
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

    public double getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }

    public double getMaxEngineForce() {
        return maxEngineForce;
    }

    public void setMaxEngineForce(double maxEngineForce) {
        this.maxEngineForce = maxEngineForce;
    }

    public double getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }

    public double getxAcceleration() {
        return xAcceleration;
    }

    public void setxAcceleration(double xAcceleration) {
        this.xAcceleration = xAcceleration;
    }

    public double getyAcceleration() {
        return yAcceleration;
    }

    public void setyAcceleration(double yAcceleration) {
        this.yAcceleration = yAcceleration;
    }

    public double getMaxBrakingForce() {
        return maxBrakingForce;
    }

    public void setMaxBrakingForce(double maxBrakingForce) {
        this.maxBrakingForce = maxBrakingForce;
    }

    public double getDragConstant() {
        return dragConstant;
    }

    public void setDragConstant(double dragConstant) {
        this.dragConstant = dragConstant;
    }

    public double getRollingResistanceConstant() {
        return rollingResistanceConstant;
    }

    public void setRollingResistanceConstant(double rollingResistanceConstant) {
        this.rollingResistanceConstant = rollingResistanceConstant;
    }
}
