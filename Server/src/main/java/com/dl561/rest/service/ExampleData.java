package com.dl561.rest.service;

import com.dl561.rest.domain.dto.SimulationDataDto;
import com.dl561.simulation.course.Course;
import com.dl561.simulation.course.location.Location;
import com.dl561.simulation.course.segment.Arc;
import com.dl561.simulation.course.segment.Rectangle;
import com.dl561.simulation.hud.Hud;
import com.dl561.simulation.vehicle.Car;
import com.dl561.simulation.vehicle.Vehicle;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ExampleData {

    public SimulationDataDto getExampleSimulationDataDto() {
        SimulationDataDto simulationDto = new SimulationDataDto();
        simulationDto.setId(0);
        simulationDto.setRunning(true);
        simulationDto.setVehicles(getExampleVehicles());
        simulationDto.setCourse(getExampleCourse());
        simulationDto.setHud(new Hud());
        return simulationDto;
    }


    private Course getExampleCourse() {
        Course course = new Course();
        course.setRectangles(getListRectangle());
        course.setArcs(getListArc());
        return course;
    }

    private List<Rectangle> getListRectangle() {
        List<Rectangle> rectangles = new LinkedList<>();
        rectangles.add(new Rectangle(10, 15, 100, 150, 10));
        return rectangles;
    }

    private List<Arc> getListArc() {
        List<Arc> arcs = new LinkedList<>();
        arcs.add(new Arc(500, 105, 10, 0, 1.5 * Math.PI, false, 0));
        return arcs;
    }

    private List<Car> getExampleVehicles() {
        List<Car> vehicles = new LinkedList<>();
        vehicles.add(getExampleVehicle());
        return vehicles;
    }

    private Car getExampleVehicle() {
        Car vehicle = new Car();
        vehicle.setId(1);
        Location location = new Location();
        location.setX(100);
        location.setY(20);
        vehicle.setLocation(location);
        vehicle.setDirectionOfTravel(210);
        vehicle.setMass(50);
        vehicle.setVelocity(21);
        return vehicle;
    }
}
