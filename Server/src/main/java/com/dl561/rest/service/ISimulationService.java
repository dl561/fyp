package com.dl561.rest.service;

import com.dl561.rest.domain.dto.SimulationDataDto;
import com.dl561.rest.domain.dto.VehicleDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ISimulationService {
    List<SimulationDataDto> getAllSimulations();

    SimulationDataDto getSimulation(int simulationId);

    SimulationDataDto startSimulation(int simulationId);

    SimulationDataDto stopSimulation(int simulationId);

    SimulationDataDto createSimulation(SimulationDataDto simulationDataDto);

    List<VehicleDto> getAllVehicles(int simulationId);

    SimulationDataDto exampleSimulationData();

    void doTick();
}
