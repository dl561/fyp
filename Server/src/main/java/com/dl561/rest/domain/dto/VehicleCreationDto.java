package com.dl561.rest.domain.dto;

import com.dl561.simulation.vehicle.VehicleType;

public class VehicleCreationDto {
    private VehicleType vehicleType;
    private boolean isComputer;

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public boolean getComputer() {
        return isComputer;
    }

    public void setComputer(boolean computer) {
        isComputer = computer;
    }

}
