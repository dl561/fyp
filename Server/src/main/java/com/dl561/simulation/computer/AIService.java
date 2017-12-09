package com.dl561.simulation.computer;

import com.dl561.simulation.Simulation;
import com.dl561.simulation.vehicle.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class AIService {

    public void getComputerInput(Simulation simulation, Vehicle vehicle) {
        //TODO: make the computer do stuff
        vehicle.setAcceleratorPedalDepth(100);
    }
}

