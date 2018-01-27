package com.dl561.rest.service;

import com.dl561.simulation.Simulation;
import com.dl561.simulation.course.Course;
import com.dl561.simulation.course.segment.Arc;
import com.dl561.simulation.course.segment.Rectangle;
import com.dl561.simulation.hud.Hud;
import com.dl561.simulation.physics.Vector2D;
import com.dl561.simulation.vehicle.Car;
import com.dl561.simulation.vehicle.Vehicle;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ExampleData {

    public Simulation getExampleSimulation() {
        Simulation simulation = new Simulation();
        simulation.setId(0);
        simulation.setRunning(true);
        simulation.setVehicles(getExampleVehicles());
        simulation.setCourse(getExampleCourse());
        simulation.setHud(new Hud());
        return simulation;
    }


    private Course getExampleCourse() {
        Course course = new Course();
        course.setRectangles(getListRectangle());
        course.setArcs(getListArc());
        return course;
    }

    private List<Rectangle> getListRectangle() {
        List<Rectangle> rectangles = new LinkedList<>();
        rectangles.add(new Rectangle(0, 10, 15, 100, 150, 10, "#FFFF00", true));
        return rectangles;
    }

    private List<Arc> getListArc() {
        List<Arc> arcs = new LinkedList<>();
        arcs.add(new Arc(500, 105, 10, 0, 1.5 * Math.PI, false, 0, "#FFFF00"));
        return arcs;
    }

    private List<Vehicle> getExampleVehicles() {
        List<Vehicle> vehicles = new LinkedList<>();
        vehicles.add(getExampleVehicle());
        return vehicles;
    }

    private Car getExampleVehicle() {
        Car vehicle = new Car();
        vehicle.setId(1);
        Vector2D location = new Vector2D();
        location.setX(100);
        location.setY(20);
        vehicle.setLocation(location);
        vehicle.setDirectionOfTravel(210);
        vehicle.setMass(50);
        vehicle.setWRXVelocity(21);
        return vehicle;
    }
}
