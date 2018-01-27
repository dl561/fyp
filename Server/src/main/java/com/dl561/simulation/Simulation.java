package com.dl561.simulation;

import com.dl561.simulation.course.Course;
import com.dl561.simulation.course.Waypoint;
import com.dl561.simulation.debug.Report;
import com.dl561.simulation.hud.Hud;
import com.dl561.simulation.hud.TextHud;
import com.dl561.simulation.physics.Collidable;
import com.dl561.simulation.vehicle.Vehicle;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Component
public class Simulation {

    private int id;
    private List<Vehicle> vehicles;
    private Course course;
    private Hud hud;
    private Boolean running;
    private Waypoint[] waypoints;
    private double runTime;
    private double currentTime;
    private double previousTickTime;

    public Simulation() {
        try {
            Report.saveHeaderLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Simulation(Simulation simulation) {
        if (simulation != null) {
            if (simulation.getVehicles() != null) {
                this.vehicles = simulation.getVehicles();
            } else {
                this.vehicles = new LinkedList<>();
            }
            if (simulation.getCourse() != null) {
                this.course = simulation.getCourse();
            } else {
                this.course = new Course();
            }
            if (simulation.getHud() != null) {
                this.hud = simulation.getHud();
            } else {
                this.hud = new Hud();
            }
            if (simulation.getRunTime() != 0) {
                this.runTime = simulation.getRunTime();
            } else {
                this.runTime = 0;
            }
            if (simulation.getRunning() != null) {
                this.running = simulation.getRunning();
            } else {
                this.running = true;
            }
        }
        this.currentTime = System.currentTimeMillis();
        this.previousTickTime = currentTime - Tick.TICK_TIME;
    }

    public List<Collidable> getAllCollidables() {
        List<Collidable> collidables = new LinkedList<>();
        collidables.addAll(vehicles);
        collidables.addAll(course.getRectangles());
        return collidables;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
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

    public Waypoint[] getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(Waypoint[] waypoints) {
        this.waypoints = waypoints;
    }

    public void start() {
        this.running = true;
    }

    public void stop() {
        this.running = false;
    }

    public void doTick() {
        if (running) {
            previousTickTime = currentTime;
            currentTime = System.currentTimeMillis();
            runTime += currentTime - previousTickTime;
            updateHud();
        }
    }

    private void updateHud() {
        List<TextHud> textHuds = new LinkedList<>();
    }
}
