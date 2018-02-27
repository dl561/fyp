package com.dl561.rest.service;

import com.dl561.rest.domain.dto.*;
import com.dl561.simulation.Simulation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ISimulationService {
    List<SimulationDto> getAllSimulations();

    SimulationDto getSimulation(int simulationId);

    SimulationDto startSimulation(int simulationId);

    SimulationDto stopSimulation(int simulationId);

    SimulationDto createSimulation(Simulation simulation);

    SimulationDto createSimulation(NewSimulationOptionsDto newSimulationOptionsDto);

    List<VehicleDto> getAllVehicles(int simulationId);

    VehicleDto getVehicleById(int simulationId, int vehicleId);

    VehicleDto updateVehicle(int simulationId, int vehicleId, VehicleUpdateDto vehicleUpdateDto);

    SimulationDto exampleSimulationData();

    void doTick();

    List<SimulationSearchResponseDto> searchForSimulations();
}
