package com.dl561.rest.domain.dto;

import java.util.List;

public class NewSimulationOptionsDto {
    private int trackNumber;
    private int numberOfLaps;
    private List<VehicleCreationDto> vehiclesToCreate;

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public List<VehicleCreationDto> getVehiclesToCreate() {
        return vehiclesToCreate;
    }

    public void setVehiclesToCreate(List<VehicleCreationDto> vehiclesToCreate) {
        this.vehiclesToCreate = vehiclesToCreate;
    }

    public int getNumberOfLaps() {
        return numberOfLaps;
    }

    public void setNumberOfLaps(int numberOfLaps) {
        this.numberOfLaps = numberOfLaps;
    }
}
