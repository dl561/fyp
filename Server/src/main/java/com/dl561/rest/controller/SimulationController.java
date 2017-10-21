package com.dl561.rest.controller;

import com.dl561.rest.domain.dto.SimulationDataDto;
import com.dl561.rest.domain.dto.VehicleDto;
import com.dl561.rest.service.ISimulationService;
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
    public ResponseEntity<List<SimulationDataDto>> getAllSimulations() {
        System.out.println("getAllSimulations");
        return new ResponseEntity<>(simulationService.getAllSimulations(), HttpStatus.OK);
    }

    @RequestMapping(value = "/simulation/{simulation_id}", method = RequestMethod.GET, produces = JSON_RESULT)
    public ResponseEntity<SimulationDataDto> getSimulationById(@PathVariable("simulation_id") Integer simulationId) {
        System.out.println("getSimulationById(" + simulationId + ")");
        return new ResponseEntity<>(simulationService.getSimulation(simulationId), HttpStatus.OK);
    }

    @RequestMapping(value = "/simulation", method = RequestMethod.PUT, produces = JSON_RESULT)
    public ResponseEntity<SimulationDataDto> createSimulation(@RequestBody SimulationDataDto simulationDataDto) {
        System.out.println("createSimulation");
        return new ResponseEntity<>(simulationService.createSimulation(simulationDataDto), HttpStatus.OK);
    }

    @RequestMapping(value = "/simulation/{simulation_id}/start", method = RequestMethod.GET, produces = JSON_RESULT)
    public ResponseEntity<SimulationDataDto> startSimulation(@PathVariable("simulation_id") Integer simulationId) {
        System.out.println("startSimulation");
        return new ResponseEntity<>(simulationService.startSimulation(simulationId),HttpStatus.OK);
    }

    @RequestMapping(value = "/simulation/{simulation_id}/stop", method = RequestMethod.GET, produces = JSON_RESULT)
    public ResponseEntity<SimulationDataDto> stopSimulation(@PathVariable("simulation_id") Integer simulationId) {
        System.out.println("endSimulation(" + simulationId + ")");
        return new ResponseEntity<>(simulationService.stopSimulation(simulationId), HttpStatus.OK);
    }

    @RequestMapping(value = "/simulation/{simulation_id}/vehicles", method = RequestMethod.GET, produces = JSON_RESULT)
    public ResponseEntity<List<VehicleDto>> getAllVehicles(@PathVariable("simulation_id") Integer simulationId) {
        System.out.println("getAllVehicles");
        return new ResponseEntity<>(simulationService.getAllVehicles(simulationId), HttpStatus.OK);
    }

    @RequestMapping(value = "/example", method = RequestMethod.GET, produces = JSON_RESULT)
    public ResponseEntity<SimulationDataDto> exampleData() {
        System.out.println("exampleData");
        return new ResponseEntity<>(simulationService.exampleSimulationData(), HttpStatus.OK);
    }
}
