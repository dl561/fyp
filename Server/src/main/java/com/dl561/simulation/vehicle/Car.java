package com.dl561.simulation.vehicle;

import org.springframework.stereotype.Component;

@Component
public class Car extends Vehicle {

    public Car() {
        this.setVehicleType(VehicleType.CAR);
    }

}
