package com.dl561.rest.service;

import com.dl561.rest.domain.dto.NewSimulationOptionsDto;
import com.dl561.rest.domain.dto.VehicleUpdateDto;
import com.dl561.simulation.Simulation;
import com.dl561.simulation.course.Course;
import com.dl561.simulation.hud.Hud;
import com.dl561.simulation.physics.Physics;
import com.dl561.simulation.vehicle.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class SimulationService implements ISimulationService {

    private final ExampleData exampleData;
    private final Physics physics;

    private List<Simulation> simulations;

    @Autowired
    public SimulationService(ExampleData exampleData, Physics physics) {
        this.exampleData = exampleData;
        this.physics = physics;
        simulations = new LinkedList<>();
    }

    @Override
    public List<Simulation> getAllSimulations() {
        return simulations;
    }

    @Override
    public Simulation getSimulation(int simulationId) {
        return simulations.get(simulationId);
        //TODO: This will throw an exception if it doesn't exist, need to handle
    }

    @Override
    public Simulation startSimulation(int simulationId) {
        simulations.get(simulationId).start();
        return simulations.get(simulationId);
    }

    @Override
    public Simulation stopSimulation(int simulationId) {
        simulations.get(simulationId).stop();
        return simulations.get(simulationId);
    }

    @Override
    public Simulation createSimulation(Simulation simulation) {
        //I assume it will be acceptable to just take what you are given and put it into the simulation
        //If you are given a blank one then you create
        simulation.setId(simulations.size());
        simulations.add(simulation);
        return simulation;
    }

    @Override
    public Simulation createSimulation(NewSimulationOptionsDto newSimulationOptionsDto) {
        Simulation simulation = new Simulation();
        simulation.setId(simulations.size());
        populateSimulation(simulation, newSimulationOptionsDto);
        simulation.setRunning(true);
        simulation.setCurrentTime(0);
        simulation.setPreviousTickTime(-10);
        simulation.setRunTime(0);
        simulation.setHud(new Hud());
        simulations.add(simulation);
        System.out.println("Finished creating simulation");
        return simulation;
    }

    private void populateSimulation(Simulation simulation, NewSimulationOptionsDto newSimulationOptionsDto) {
        simulation.setCourse(Course.getByTrackNumber(newSimulationOptionsDto.getTrackNumber()));
        simulation.setVehicles(Course.getVehicles(newSimulationOptionsDto.getVehiclesToCreate()));
    }

    @Override
    public List<Vehicle> getAllVehicles(int simulationId) {
        return simulations.get(simulationId).getVehicles();
    }

    @Override
    public Vehicle getVehicleById(int simulationId, int vehicleId) {
        //TODO: This will also error if it doesn't exist
        return simulations.get(simulationId).getVehicles().get(vehicleId);
    }

    @Override
    public Vehicle updateVehicle(int simulationId, int vehicleId, VehicleUpdateDto vehicleUpdateDto) {
        return simulations.get(simulationId).getVehicles().get(vehicleId).update(vehicleUpdateDto);
    }


    @Override
    public Simulation exampleSimulationData() {
        return exampleData.getExampleSimulation();
    }

    @Override
    public void doTick() {
        for (Simulation simulation : simulations) {
            physics.calculateNewPositions(simulation);
            simulation.doTick();
        }
    }
}
