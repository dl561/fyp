package com.dl561.rest.domain.dto;

import java.util.List;

public class SimulationSearchResponseDto {

    private int id;
    private String name;
    private int trackNumber;
    private int numberOfLaps;
    private List<VehicleSearchResponseDto> vehicles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public int getNumberOfLaps() {
        return numberOfLaps;
    }

    public void setNumberOfLaps(int numberOfLaps) {
        this.numberOfLaps = numberOfLaps;
    }

    public List<VehicleSearchResponseDto> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<VehicleSearchResponseDto> vehicles) {
        this.vehicles = vehicles;
    }
}
