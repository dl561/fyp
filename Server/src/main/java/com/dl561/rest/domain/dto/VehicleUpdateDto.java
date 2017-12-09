package com.dl561.rest.domain.dto;

public class VehicleUpdateDto {
    private int id;
    private double steeringWheelOrientation;
    private double acceleratorPedalDepth;
    private double brakePedalDepth;
    private int gear;
    private boolean frontSlip;
    private boolean rearSlip;

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

    public int getGear() {
        return gear;
    }

    public void setGear(int gear) {
        this.gear = gear;
    }

    public boolean isFrontSlip() {
        return frontSlip;
    }

    public void setFrontSlip(boolean frontSlip) {
        this.frontSlip = frontSlip;
    }

    public boolean isRearSlip() {
        return rearSlip;
    }

    public void setRearSlip(boolean rearSlip) {
        this.rearSlip = rearSlip;
    }

    @Override
    public String toString() {
        return "VehicleUpdateDto{" +
                "id=" + id +
                ", steeringWheelOrientation=" + steeringWheelOrientation +
                ", acceleratorPedalDepth=" + acceleratorPedalDepth +
                ", brakePedalDepth=" + brakePedalDepth +
                ", gear=" + gear +
                ", frontSlip=" + frontSlip +
                ", rearSlip=" + rearSlip +
                '}';
    }
}
