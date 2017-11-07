package com.dl561.simulation.vehicle;

import com.dl561.simulation.course.location.Location;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Car extends Vehicle {

    @Value("${vehicle.car.length}")
    private double length = 50;
    @Value("${vehicle.car.width}")
    private double width = 30;
    @Value("${vehicle.car.maxengineforce}")
    private double maxEngineForce = 6d;
    @Value("${vehicle.car.maxbrakingforce}")
    private double maxBrakingForce = 8d;
    @Value("${vehicle.car.dragresistance}")
    private double dragResistance = 0.4d;
    @Value("${vehicle.car.rollingresistance}")
    private double rollingResistance = 12d;
    @Value("${vehicle.car.mass}")
    private double mass = 900d;
    @Value("${vehicle.car.wheelbase}")
    private double wheelBase = 4d;
    @Value("${vehicle.car.centreofgravityheight}")
    private double centreOfGravityHeight = 1d;
    @Value("${vehicle.car.differentialratio}")
    private double differentialRatio = 3.4d;
    @Value("${vehicle.car.transmissionefficiency}")
    private double transmissionEfficiency = 0.7d;
    @Value("${vehicle.car.wheelradius}")
    private double wheelRadius = 0.34d;
    @Value("${vehicle.car.gearratios}")
    private double[] gearRatios = {-2.90d, 2.66d, 1.78d, 1.30d, 1.0d, 0.74d};

    public Car() {
        setValues();
    }

    public Car(double x, double y, double rotation) {
        setValues();
        this.setLocation(new Location(x, y));
        this.setDirectionOfTravel(rotation);
    }

    private void setValues() {
        this.setVehicleType(VehicleType.CAR);
        this.setMaxEngineForce(maxEngineForce);
        this.setMaxBrakingForce(maxBrakingForce);
        this.setDragConstant(dragResistance);
        this.setRollingResistanceConstant(rollingResistance);
        this.setMass(mass);
        this.setWheelBaseConstant(wheelBase);
        this.setCentreOfGravityHeight(centreOfGravityHeight);
    }

}
