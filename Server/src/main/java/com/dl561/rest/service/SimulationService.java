package com.dl561.rest.service;

import com.dl561.rest.domain.converter.SimulationConverter;
import com.dl561.rest.domain.dto.SimulationDataDto;
import com.dl561.rest.domain.dto.VehicleDto;
import com.dl561.simulation.Simulation;
import com.dl561.simulation.physics.Physics;
import com.dl561.simulation.vehicle.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class SimulationService implements ISimulationService {

    private final SimulationConverter simulationConverter;
    private final ExampleData exampleData;
    private final Physics physics;

    private List<Simulation> simulations;

    @Autowired
    public SimulationService(SimulationConverter simulationConverter, ExampleData exampleData, Physics physics) {
        this.simulationConverter = simulationConverter;
        this.exampleData = exampleData;
        this.physics = physics;
        simulations = new LinkedList<>();
    }

    @Override
    public List<SimulationDataDto> getAllSimulations() {
        return simulationConverter.convertToSimulationDto(simulations);
    }

    @Override
    public SimulationDataDto getSimulation(int simulationId) {
        return simulationConverter.convertToSimulationDto(simulations.get(simulationId));
        //TODO: This will throw an exception if it doesn't exist, need to handle
    }

    @Override
    public SimulationDataDto startSimulation(int simulationId) {
        simulations.get(simulationId).start();
        return simulationConverter.convertToSimulationDto(simulations.get(simulationId));
    }

    @Override
    public SimulationDataDto stopSimulation(int simulationId) {
        simulations.get(simulationId).stop();
        return simulationConverter.convertToSimulationDto(simulations.get(simulationId));
    }

    @Override
    public SimulationDataDto createSimulation(SimulationDataDto simulationDataDto) {
        //TODO: how to use the simulation you are given!?!
        //I assume it will be acceptable to just take what you are given and put it into the simulation
        //If you are given a blank one then you create
        Simulation simulation = new Simulation(simulationDataDto);
        simulation.setId(simulations.size());
        simulations.add(simulation);
        return simulationConverter.convertToSimulationDto(simulation);
    }

    @Override
    public List<VehicleDto> getAllVehicles(int simulationId) {
        return simulationConverter.convertToVehicleDto(simulations.get(simulationId).getVehicles());
    }

    @Override
    public VehicleDto getVehicleById(int simulationId, int vehicleId) {
        //TODO: This will also error if it doesn't exist
        return simulationConverter.convertToVehicleDto(simulations.get(simulationId).getVehicles().get(vehicleId));
    }

    @Override
    public VehicleDto updateVehicle(int simulationId, int vehicleId, VehicleDto vehicleDto) {
        Vehicle vehicle = simulationConverter.convertToVehicle(vehicleDto);
        return simulationConverter.convertToVehicleDto(simulations.get(simulationId).getVehicles().get(vehicleId).update(vehicle));
    }


    @Override
    public SimulationDataDto exampleSimulationData() {
        return exampleData.getExampleSimulationDataDto();
    }

    @Override
    public void doTick() {
        for (Simulation simulation : simulations) {
            simulation.doTick();
            physics.calculateNewPositions(simulation);
        }
    }
}
