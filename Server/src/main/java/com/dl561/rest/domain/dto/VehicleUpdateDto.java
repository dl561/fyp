package com.dl561.rest.domain.dto;

public class VehicleUpdateDto {
    private int id;
    private double steeringWheelOrientation;
    private double acceleratorPedalDepth;
    private double brakePedalDepth;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSteeringWheelOrientation() {
        return steeringWheelOrientation;
    }

    public void setSteeringWheelOrientation(double steeringWheelOrientation) {
        this.steeringWheelOrientation = steeringWheelOrientation;
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
}
