package com.dl561.rest.service;

import com.dl561.simulation.Simulation;
import com.dl561.simulation.vehicle.Vehicle;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ISimulationService {
    List<Simulation> getAllSimulations();

    Simulation getSimulation(int simulationId);

    Simulation startSimulation(int simulationId);

    Simulation stopSimulation(int simulationId);

    Simulation createSimulation(Simulation simulation);

    List<Vehicle> getAllVehicles(int simulationId);

    Vehicle getVehicleById(int simulationId, int vehicleId);

    Vehicle updateVehicle(int simulationId, int vehicleId, Vehicle vehicle);

    Simulation exampleSimulationData();

    void doTick();
}
