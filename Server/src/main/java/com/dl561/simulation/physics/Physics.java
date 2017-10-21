package com.dl561.simulation.physics;

import com.dl561.simulation.Simulation;
import com.dl561.simulation.vehicle.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class Physics {

    public void calculateNewPositions(Simulation simulation) {
        System.out.println("Calculating new positions");
        for (Vehicle vehicle : simulation.getVehicles()) {
            vehicle.getLocation().setX(vehicle.getLocation().getX() + 1);
            vehicle.setDirectionOfTravel(vehicle.getDirectionOfTravel() + 1);
        }
    }
}
