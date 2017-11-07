package com.dl561.rest.domain.dto;

import com.dl561.simulation.vehicle.VehicleType;

public class VehicleCreationDto {
    private VehicleType vehicleType;
    private int count;

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
