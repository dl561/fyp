package com.dl561.simulation.vehicle;

import com.dl561.rest.domain.dto.VehicleUpdateDto;
import com.dl561.simulation.course.location.Location;
import org.springframework.stereotype.Component;

@Component
public abstract class Vehicle {
    private int id;
    private Location location;
    private double directionOfTravel;
    private double xVelocity = 0;
    private double yVelocity = 0;
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
    private double wheelBaseConstant;
    private double centreOfGravityHeight;
    private double differentialRatio;
    private double transmissionEfficiency;
    private double wheelRadius;
    private double[] gearRatios;

    public Vehicle update(VehicleUpdateDto vehicleUpdateDto) {
        this.steeringWheelDirection = vehicleUpdateDto.getSteeringWheelOrientation();
        this.acceleratorPedalDepth = vehicleUpdateDto.getAcceleratorPedalDepth();
        this.brakePedalDepth = vehicleUpdateDto.getBrakePedalDepth();
        return this;
    }

    public double getMaxEngineTorque(double RPM) {
        //TODO: Find out how to make a graph into a RPM to torque conversion.
        return 450d;
    }

    public double getGearRatio(int gearNumber) {
        return this.gearRatios[gearNumber];
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

    public double getOppositeDirectionOfTravel() {
        return (180 + this.directionOfTravel) % 360;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getWeight() {
        return mass * 9.81d;
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

    public double getXVelocity() {
        return xVelocity;
    }

    public void setXVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }

    public double getMaxEngineForce() {
        return maxEngineForce;
    }

    public void setMaxEngineForce(double maxEngineForce) {
        this.maxEngineForce = maxEngineForce;
    }

    public double getYVelocity() {
        return yVelocity;
    }

    public void setYVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
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

    public double getWheelBaseConstant() {
        return wheelBaseConstant;
    }

    public void setWheelBaseConstant(double wheelBaseConstant) {
        this.wheelBaseConstant = wheelBaseConstant;
    }

    public double getCentreOfGravityHeight() {
        return centreOfGravityHeight;
    }

    public void setCentreOfGravityHeight(double centreOfGravityHeight) {
        this.centreOfGravityHeight = centreOfGravityHeight;
    }

    public double getDifferentialRatio() {
        return differentialRatio;
    }

    public void setDifferentialRatio(double differentialRatio) {
        this.differentialRatio = differentialRatio;
    }

    public double getTransmissionEfficiency() {
        return transmissionEfficiency;
    }

    public void setTransmissionEfficiency(double transmissionEfficiency) {
        this.transmissionEfficiency = transmissionEfficiency;
    }

    public double getWheelRadius() {
        return wheelRadius;
    }

    public void setWheelRadius(double wheelRadius) {
        this.wheelRadius = wheelRadius;
    }

    public double[] getGearRatios() {
        return gearRatios;
    }

    public void setGearRatios(double[] gearRatios) {
        this.gearRatios = gearRatios;
    }
}
