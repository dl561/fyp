package com.dl561.simulation.vehicle;

import org.springframework.stereotype.Component;

@Component
public class Car extends Vehicle {

    private static final double MAX_ACCELERATION = 2;
    private static final double MAX_BRAKING = -3;
    private static final double DRAG_RESISTANCE = -1;
    private static final double ROLLING_RESISTANCE = -30;

    public Car() {
        this.setVehicleType(VehicleType.CAR);
        this.setMaxEngineForce(MAX_ACCELERATION);
        this.setMaxBrakingForce(MAX_BRAKING);
        this.setDragConstant(DRAG_RESISTANCE);
        this.setRollingResistanceConstant(ROLLING_RESISTANCE);
    }

}
