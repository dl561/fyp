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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class SimulationService implements ISimulationService {

    private final ExampleData exampleData;
    private final Physics physics;

    private List<Simulation> simulations;

    private final Lock lock = new ReentrantLock();

    @Autowired
    public SimulationService(ExampleData exampleData, Physics physics) {
        this.exampleData = exampleData;
        this.physics = physics;
        simulations = new LinkedList<>();
    }

    /**
     * @return List<Simulation>     All simulations currently available
     */
    @Override
    public List<Simulation> getAllSimulations() {
        return simulations;
    }

    /**
     * Gets the simulation of id = simulationId
     *
     * @param simulationId The id of the simulation requested
     * @return Simulation
     */
    @Override
    public Simulation getSimulation(int simulationId) {
        return simulations.get(simulationId);
        //TODO: This will throw an exception if it doesn't exist, need to handle
    }

    /**
     * Used to start the simulation specified by simulationId, if it is running it carries on running
     *
     * @param simulationId
     * @return Simulation   Returns the updated simulation
     */
    @Override
    public Simulation startSimulation(int simulationId) {
        simulations.get(simulationId).start();
        return simulations.get(simulationId);
    }

    /**
     * Used to stop the simulation specified by simulationId, if it is stopped it stays stopped
     *
     * @param simulationId
     * @return Simulation   Returns the updated simulation
     */
    @Override
    public Simulation stopSimulation(int simulationId) {
        simulations.get(simulationId).stop();
        return simulations.get(simulationId);
    }

    /**
     * Used to create a Simulation object from an old Simulation object. This would be used to reload old simulations
     *
     * @param simulation The old simulation to be reloaded
     * @return Simulation   The new copy of the simulation that has been loaded
     */
    @Override
    public Simulation createSimulation(Simulation simulation) {
        //I assume it will be acceptable to just take what you are given and put it into the simulation
        //If you are given a blank one then you create
        simulation.setId(simulations.size());
        lock.lock();
        try {
            simulations.add(simulation);
        } finally {
            lock.unlock();
        }
        return simulation;
    }

    /**
     * Creating a simulation object using a NewSimulationOptionsDto, this is how the front end will generally create a new simulation
     *
     * @param newSimulationOptionsDto The options used in the new simulation
     * @return Simulation  The new simulation object that has been created
     */
    @Override
    public Simulation createSimulation(NewSimulationOptionsDto newSimulationOptionsDto) {
        Simulation simulation = new Simulation();
        simulation.setId(simulations.size());
        populateSimulation(simulation, newSimulationOptionsDto);
        simulation.setRunning(false);
        simulation.setCurrentTime(0);
        simulation.setPreviousTickTime(-10);
        simulation.setRunTime(0);
        simulation.setHud(new Hud());
        simulation.setNumberOfLaps(newSimulationOptionsDto.getNumberOfLaps());
        lock.lock();
        try {
            simulations.add(simulation);
        } finally {
            lock.unlock();
        }
        System.out.println("Finished creating simulation");
        return simulation;
    }

    /**
     * Populates the simulation with course, vehicle and waypoint data
     *
     * @param simulation              The simulation to add this data to
     * @param newSimulationOptionsDto The options that will be used to populate the simulation
     */
    private void populateSimulation(Simulation simulation, NewSimulationOptionsDto newSimulationOptionsDto) {
        simulation.setCourse(Course.getByTrackNumber(newSimulationOptionsDto.getTrackNumber()));
        simulation.setVehicles(Vehicle.getVehicles(newSimulationOptionsDto.getVehiclesToCreate()));
        simulation.setWaypoints(Course.getWayPointsByTrackNumber(newSimulationOptionsDto.getTrackNumber()));
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

    /**
     * This runs every tick, it means every simulation has physics running on it
     */
    @Override
    public void doTick() {
        lock.lock();
        try {
            for (Simulation simulation : simulations) {
                physics.simulate(simulation);
                simulation.doTick();
            }
        } finally {
            lock.unlock();
        }
    }

    public List<Integer> getAllSimulationIds() {
        List<Integer> ids = new LinkedList<>();
        lock.lock();
        try {
            for (Simulation simulation : simulations) {
                ids.add(simulation.getId());
            }
        } finally {
            lock.unlock();
        }
        return ids;
    }
}
