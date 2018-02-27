package com.dl561.rest.domain.dto;

public class VehicleSearchResponseDto {
    private int id;
    private boolean isComputer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isComputer() {
        return isComputer;
    }

    public void setComputer(boolean computer) {
        isComputer = computer;
    }
}
