package com.dl561.simulation;

import com.dl561.rest.domain.dto.SimulationDataDto;
import com.dl561.simulation.course.Course;
import com.dl561.simulation.hud.Hud;
import com.dl561.simulation.vehicle.Car;
import com.dl561.simulation.vehicle.Vehicle;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class Simulation {

    private int id;
    private List<Car> vehicles;
    private Course course;
    private Hud hud;
    private Boolean running;
    private double runTime;
    private double currentTime;
    private double previousTickTime;

    public Simulation() {

    }

    public Simulation(SimulationDataDto simulationDataDto) {
        if (simulationDataDto != null) {
            if (simulationDataDto.getVehicles() != null) {
                this.vehicles = simulationDataDto.getVehicles();
            } else {
                this.vehicles = new LinkedList<>();
            }
            if (simulationDataDto.getCourse() != null) {
                this.course = simulationDataDto.getCourse();
            } else {
                this.course = new Course();
            }
            if (simulationDataDto.getHud() != null) {
                this.hud = simulationDataDto.getHud();
            } else {
                this.hud = new Hud();
            }
            if (simulationDataDto.getRunTime() != 0) {
                this.runTime = simulationDataDto.getRunTime();
            } else {
                this.runTime = 0;
            }
            if (simulationDataDto.getRunning() != null) {
                this.running = simulationDataDto.getRunning();
            } else {
                this.running = true;
            }
        }
        this.currentTime = System.currentTimeMillis();
        this.previousTickTime = currentTime - Tick.TICK_TIME;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Car> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Car> vehicles) {
        this.vehicles = vehicles;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Hud getHud() {
        return hud;
    }

    public void setHud(Hud hud) {
        this.hud = hud;
    }

    public Boolean getRunning() {
        return running;
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }

    public double getRunTime() {
        return runTime;
    }

    public void setRunTime(double runTime) {
        this.runTime = runTime;
    }

    public double getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(double currentTime) {
        this.currentTime = currentTime;
    }

    public double getPreviousTickTime() {
        return previousTickTime;
    }

    public void setPreviousTickTime(double previousTickTime) {
        this.previousTickTime = previousTickTime;
    }

    public void start() {
        this.running = true;
    }

    public void stop() {
        this.running = false;
    }

    public void doTick() {
        if (running) {
            currentTime = System.currentTimeMillis();
            runTime += currentTime - previousTickTime;
        }
    }
}
