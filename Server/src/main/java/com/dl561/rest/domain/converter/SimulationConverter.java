package com.dl561.rest.domain.converter;

import com.dl561.rest.domain.dto.SimulationDataDto;
import com.dl561.rest.domain.dto.VehicleDto;
import com.dl561.simulation.Simulation;
import com.dl561.simulation.vehicle.Car;
import com.dl561.simulation.vehicle.Vehicle;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class SimulationConverter {

    public SimulationDataDto convertToSimulationDto(Simulation simulation) {
        SimulationDataDto result = new SimulationDataDto();
        result.setId(simulation.getId());
        result.setVehicles(simulation.getVehicles());
        result.setCourse(simulation.getCourse());
        result.setRunning(simulation.getRunning());
        result.setHud(simulation.getHud());
        result.setCurrentTime(simulation.getCurrentTime());
        result.setPreviousTickTime(simulation.getPreviousTickTime());
        result.setRunTime(simulation.getRunTime());
        return result;
    }

    public Simulation convertToSimulation(SimulationDataDto simulationDataDto) {
        Simulation result = new Simulation();
        result.setId(simulationDataDto.getId());
        result.setVehicles(simulationDataDto.getVehicles());
        result.setCourse(simulationDataDto.getCourse());
        result.setRunning(simulationDataDto.getRunning());
        result.setHud(simulationDataDto.getHud());
        result.setCurrentTime(simulationDataDto.getCurrentTime());
        result.setPreviousTickTime(simulationDataDto.getPreviousTickTime());
        result.setRunTime(simulationDataDto.getRunTime());
        return result;
    }

    public List<SimulationDataDto> convertToSimulationDto(List<Simulation> simulations) {
        List<SimulationDataDto> result = new LinkedList<>();
        for (Simulation simulation : simulations) {
            result.add(convertToSimulationDto(simulation));
        }
        return result;
    }

    public List<Simulation> convertToSimulation(List<SimulationDataDto> simulationDataDtos) {
        List<Simulation> result = new LinkedList<>();
        for (SimulationDataDto simulationDataDto : simulationDataDtos) {
            result.add(convertToSimulation(simulationDataDto));
        }
        return result;
    }

    public VehicleDto convertToVehicleDto(Car vehicle) {
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setId(vehicle.getId());
        vehicleDto.setLocation(vehicle.getLocation());
        vehicleDto.setDirectionOfTravel(vehicle.getDirectionOfTravel());
        vehicleDto.setMass(vehicle.getMass());
        vehicleDto.setVelocity(vehicle.getVelocity());
        return vehicleDto;
    }

    public List<VehicleDto> convertToVehicleDto(List<Car> vehicles) {
        List<VehicleDto> vehicleDtos = new LinkedList<>();
        for (Car vehicle : vehicles) {
            vehicleDtos.add(convertToVehicleDto(vehicle));
        }
        return vehicleDtos;
    }
}
