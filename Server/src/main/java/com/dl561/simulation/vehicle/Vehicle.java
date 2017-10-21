package com.dl561.simulation.vehicle;

import com.dl561.simulation.course.location.Location;
import org.springframework.stereotype.Component;

@Component
public abstract class Vehicle {
    private int id;
    private Location location;
    private double directionOfTravel;
    private double velocity;
    private double mass;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public double getDirectionOfTravel() {
        return directionOfTravel;
    }

    public void setDirectionOfTravel(double directionOfTravel) {
        this.directionOfTravel = directionOfTravel;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }
}
