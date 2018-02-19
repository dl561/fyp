package com.dl561.simulation;

import com.dl561.simulation.computer.AIService;
import com.dl561.simulation.course.Course;
import com.dl561.simulation.course.Waypoint;
import com.dl561.simulation.debug.Report;
import com.dl561.simulation.hud.Hud;
import com.dl561.simulation.hud.TextHud;
import com.dl561.simulation.physics.Collidable;
import com.dl561.simulation.vehicle.Vehicle;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
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
    private int numberOfLaps;
    private double runTime;
    private double currentTime;
    private double previousTickTime;
    private AIService aiService = new AIService();

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

    public int getNumberOfLaps() {
        return numberOfLaps;
    }

    public void setNumberOfLaps(int numberOfLaps) {
        this.numberOfLaps = numberOfLaps;
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
            checkWaypointForAllVehicles();
            checkFinishedState();
            if (Tick.getTickCount() % 10 == 0) {
                updateComputerVehicles();
            }
            updatePositions();
        }
    }

    private void updateHud() {
        List<TextHud> textHuds = new LinkedList<>();
    }

    private void updateComputerVehicles() {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.isComputer()) {
                aiService.getComputerInput(this, vehicle);
            }
        }
    }

    private void checkWaypointForAllVehicles() {
        for (Vehicle vehicle : vehicles) {
            checkWaypointForVehicle(vehicle);
        }
    }

    private void checkWaypointForVehicle(Vehicle vehicle) {
        int current = vehicle.getWaypointNumber();
        int next = current + 1;
        int previous = current - 1;
        next = next >= waypoints.length ? 0 : next;
        previous = previous < 0 ? waypoints.length - 1 : previous;

        double distanceToCurrent = waypoints[current].findDistanceToWaypoint(vehicle.getLocation());
        double distanceToNext = waypoints[next].findDistanceToWaypoint(vehicle.getLocation());
        double distanceToPrevious = waypoints[previous].findDistanceToWaypoint(vehicle.getLocation());

        if (distanceToNext < distanceToCurrent) {
            //closer to the next one
            vehicle.setWaypointNumber(next);
            if (current == waypoints.length - 1) {
                //end of lap
                vehicle.setLap(vehicle.getLap() + 1);
            }
        } else if (distanceToPrevious < distanceToCurrent) {
            //closer to the previous one
            vehicle.setWaypointNumber(previous);
            if (current == 0) {
                //going backwards through laps
                vehicle.setLap(vehicle.getLap() - 1);
            }
        }
    }

    private void checkFinishedState() {
        boolean shouldContinue = false;
        for (Vehicle vehicle : vehicles) {
            if (!vehicle.isFinished()) {
                shouldContinue = true;
            }
            if (vehicle.getLap() >= numberOfLaps) {
                //Vehicle has finished
                vehicle.setFinished(true);
            }
        }
        if (shouldContinue) {
            //everyone has finished and so the race should finish
            finishRace();
        }
    }

    private void finishRace() {
        //TODO: Add finish code here. The positions of each vehicle when it finished are in the vehicle objects
    }

    private void updatePositions() {
        //TODO: check this because if you finish and move past someone you shouldn't take over them in the positions
        //Simply add all of them to a list and then sort the list on lap, waypoint then distance to next
        List<PositionNode> positionNodes = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            double distanceToNextWaypoint = waypoints[vehicle.getWaypointNumber()].findDistanceToWaypoint(vehicle.getLocation());
            positionNodes.add(new PositionNode(vehicle, vehicle.getLap(), vehicle.getWaypointNumber(), distanceToNextWaypoint));
        }
        positionNodes.sort((o1, o2) -> {
            //TODO: check this Comparator, might be doing it in reverse
            int comparison;
            if (o1.lap > o2.lap) {
                comparison = 0;
            } else if (o2.lap > o1.lap) {
                comparison = 1;
            } else {
                if (o1.waypoint > o2.waypoint) {
                    comparison = 0;
                } else if (o2.waypoint > o1.waypoint) {
                    comparison = 1;
                } else {
                    if (o1.distanceToNextWaypoint < o2.distanceToNextWaypoint) {
                        comparison = 0;
                    } else if (o2.distanceToNextWaypoint < o2.distanceToNextWaypoint) {
                        comparison = 1;
                    } else {
                        comparison = 0;
                    }
                }
            }
            return comparison;
        });
        int count = 1;
        for (PositionNode positionNode : positionNodes) {
            positionNode.vehicle.setPosition(count);
            count++;
        }
    }
}
