package com.dl561.rest.domain.dto;

import com.dl561.simulation.vehicle.VehicleType;

public class VehicleCreationDto {
    private VehicleType vehicleType;
    private String isComputer;

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getComputer() {
        return isComputer;
    }

    public void setComputer(String computer) {
        isComputer = computer;
    }

    public boolean isComputer() {
        return this.isComputer != null && this.isComputer.equalsIgnoreCase("true");
    }
}
