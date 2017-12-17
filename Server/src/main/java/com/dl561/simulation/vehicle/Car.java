package com.dl561.simulation.vehicle;

import com.dl561.simulation.course.location.Location;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Car extends Vehicle {

    @Value("${vehicle.car.length}")
    private double length = 125;
    @Value("${vehicle.car.width}")
    private double width = 50;
    @Value("${vehicle.car.maxengineforce}")
    private double maxEngineForce = 600d;
    @Value("${vehicle.car.maxbrakingforce}")
    private double maxBrakingForce = 800d;
    @Value("${vehicle.car.dragresistance}")
    private double dragResistance = 500d;
    @Value("${vehicle.car.rollingresistance}")
    private double rollingResistance = 3000d;
    @Value("${vehicle.car.mass}")
    private double mass = 1500d;
    private double frontWheelBase = 1d;
    private double rearWheelBase = 1d;
    @Value("${vehicle.car.wheelbase}")
    private double wheelBase = frontWheelBase + rearWheelBase;
    @Value("${vehicle.car.centreofgravityheight}")
    private double centreOfGravityHeight = 1d;
    //   @Value("${vehicle.car.differentialratio}")
    private double differentialRatio = 3.4d;
    //  @Value("${vehicle.car.transmissionefficiency}")
    private double transmissionEfficiency = 0.7d;
    // @Value("${vehicle.car.wheelradius}")
    private double wheelRadius = 0.34d;
    // @Value("${vehicle.car.gearratios}")
    //private double[] gearRatios = {-2.90d, 2.66d, 1.78d, 1.30d, 1.0d, 0.74d};
    private double[] gearRatios = {-2.90d, 2.66d, 3.50d, 4.20d, 5.00d, 6.80d};

    private double inertia = 3000d;

    public Car() {
        setValues();
    }

    public Car(int id, double x, double y, double rotation, boolean isComputer) {
        setValues();
        this.setLocation(new Location(x, y));
        this.setDirectionOfTravel(rotation);
        this.setComputer(isComputer);
        setId(id);
    }

    private void setValues() {
        setVehicleType(VehicleType.CAR);
        setMaxEngineForce(maxEngineForce);
        setMaxBrakingForce(maxBrakingForce);
        setDragConstant(dragResistance);
        setRollingResistanceConstant(rollingResistance);
        setMass(mass);
        setWheelBaseConstant(wheelBase);
        setCentreOfGravityHeight(centreOfGravityHeight);
        setDifferentialRatio(differentialRatio);
        setTransmissionEfficiency(transmissionEfficiency);
        setWheelRadius(wheelRadius);
        setGearRatios(gearRatios);
        setGear(1);
        setInertia(inertia);
        setFrontWheelBase(frontWheelBase);
        setRearWheelBase(rearWheelBase);
        setWidth(width);
        setLength(length);
    }

}
