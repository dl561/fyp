package com.dl561.rest.controller;

import com.dl561.rest.domain.dto.VehicleUpdateDto;
import com.dl561.rest.service.ISimulationService;
import com.dl561.simulation.Simulation;
import com.dl561.simulation.vehicle.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@ComponentScan
public class SimulationController {

    private final ISimulationService simulationService;
    private static final String JSON_RESULT = "application/json";

    @Autowired
    public SimulationController(ISimulationService simulationService) {
        this.simulationService = simulationService;
    }

    @RequestMapping(value = "/simulation", method = RequestMethod.GET, produces = JSON_RESULT)
    public ResponseEntity<List<Simulation>> getAllSimulations() {
        System.out.println("getAllSimulations");
        return new ResponseEntity<>(simulationService.getAllSimulations(), HttpStatus.OK);
    }

    @RequestMapping(value = "/simulation/{simulation_id}", method = RequestMethod.GET, produces = JSON_RESULT)
    public ResponseEntity<Simulation> getSimulationById(@PathVariable("simulation_id") Integer simulationId) {
        System.out.println("getSimulationById(" + simulationId + ")");
        return new ResponseEntity<>(simulationService.getSimulation(simulationId), HttpStatus.OK);
    }

    @RequestMapping(value = "/simulation", method = RequestMethod.PUT, produces = JSON_RESULT)
    public ResponseEntity<Simulation> createSimulation(@RequestBody Simulation simulation) {
        System.out.println("createSimulation");
        return new ResponseEntity<>(simulationService.createSimulation(simulation), HttpStatus.OK);
    }

    @RequestMapping(value = "/simulation/{simulation_id}/start", method = RequestMethod.GET, produces = JSON_RESULT)
    public ResponseEntity<Simulation> startSimulation(@PathVariable("simulation_id") Integer simulationId) {
        System.out.println("startSimulation");
        return new ResponseEntity<>(simulationService.startSimulation(simulationId), HttpStatus.OK);
    }

    @RequestMapping(value = "/simulation/{simulation_id}/stop", method = RequestMethod.GET, produces = JSON_RESULT)
    public ResponseEntity<Simulation> stopSimulation(@PathVariable("simulation_id") Integer simulationId) {
        System.out.println("endSimulation(" + simulationId + ")");
        return new ResponseEntity<>(simulationService.stopSimulation(simulationId), HttpStatus.OK);
    }

    @RequestMapping(value = "/simulation/{simulation_id}/vehicles", method = RequestMethod.GET, produces = JSON_RESULT)
    public ResponseEntity<List<Vehicle>> getAllVehicles(@PathVariable("simulation_id") Integer simulationId) {
        System.out.println("getAllVehicles");
        return new ResponseEntity<>(simulationService.getAllVehicles(simulationId), HttpStatus.OK);
    }

    @RequestMapping(value = "/simulation/{simulation_id}/vehicle/{vehicle_id}", method = RequestMethod.GET, produces = JSON_RESULT)
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable("simulation_id") Integer simulationId, @PathVariable("vehicle_id") Integer vehicleId) {
        System.out.println("getVehicleById(" + vehicleId + ")");
        return new ResponseEntity<>(simulationService.getVehicleById(simulationId, vehicleId), HttpStatus.OK);
    }

    @RequestMapping(value = "/simulation/{simulation_id}/vehicle/{vehicle_id}", method = RequestMethod.POST, produces = JSON_RESULT)
    public ResponseEntity<Vehicle> updateVehicleById(@PathVariable("simulation_id") Integer simulationId, @PathVariable("vehicle_id") Integer vehicleId, @RequestBody VehicleUpdateDto vehicleUpdateDto) {
        System.out.println("updateVehicleById(" + vehicleId + ")");
        return new ResponseEntity<>(simulationService.updateVehicle(simulationId, vehicleId, vehicleUpdateDto), HttpStatus.OK);
    }

    @RequestMapping(value = "/example", method = RequestMethod.GET, produces = JSON_RESULT)
    public ResponseEntity<Simulation> exampleData() {
        System.out.println("exampleData");
        return new ResponseEntity<>(simulationService.exampleSimulationData(), HttpStatus.OK);
    }
}
