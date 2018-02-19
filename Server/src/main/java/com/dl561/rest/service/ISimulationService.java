package com.dl561.rest.service;

import com.dl561.rest.domain.dto.NewSimulationOptionsDto;
import com.dl561.rest.domain.dto.VehicleUpdateDto;
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

    Simulation createSimulation(NewSimulationOptionsDto newSimulationOptionsDto);

    List<Vehicle> getAllVehicles(int simulationId);

    Vehicle getVehicleById(int simulationId, int vehicleId);

    Vehicle updateVehicle(int simulationId, int vehicleId, VehicleUpdateDto vehicleUpdateDto);

    Simulation exampleSimulationData();

    void doTick();

    List<Integer> getAllSimulationIds();
}
