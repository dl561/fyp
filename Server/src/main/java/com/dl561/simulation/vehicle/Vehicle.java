package com.dl561.simulation.vehicle;

import com.dl561.rest.domain.dto.VehicleCreationDto;
import com.dl561.rest.domain.dto.VehicleUpdateDto;
import com.dl561.simulation.physics.Collidable;
import com.dl561.simulation.physics.Physics;
import com.dl561.simulation.physics.Vector2D;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public abstract class Vehicle extends Collidable {
    private int id;
    //variable
    private Vector2D location;
    private double directionOfTravel;
    private double steeringWheelDirection;
    private double acceleratorPedalDepth;
    private double brakePedalDepth;
    private int gear;
    private double angularAcceleration;
    private double angularVelocity;
    private double worldReferenceXVelocity = 0;
    private double worldReferenceYVelocity = 0;
    private double torque;
    private double worldReferenceXAcceleration;
    private double worldReferenceYAcceleration;
    private double vehicleReferenceXAcceleration;
    private double vehicleReferenceYAcceleration;
    private boolean frontSlip = false;
    private boolean rearSlip = false;
    private boolean isComputer = false;
    private double coefficientOfResitution;
    private int waypointNumber = 0;
    private int lap = 0;
    private boolean finished = false;
    private int position = id + 1;
    private int rpm = 1000;
    //constant
    private double width;
    private double length;
    private double mass;
    private VehicleType vehicleType;
    private double maxEngineForce;
    private double maxBrakingForce;
    private double dragConstant;
    private double rollingResistanceConstant;
    private double wheelBaseConstant;
    private double frontWheelBase;
    private double rearWheelBase;
    private double centreOfGravityHeight;
    private double differentialRatio;
    private double transmissionEfficiency;
    private double wheelRadius;
    private double[] gearRatios;
    private double inertia;

    public static final double MAXIMUM_STEER_ANGLE = Math.PI / 6;

    public Vehicle update(VehicleUpdateDto vehicleUpdateDto) {
        if (!finished) {
            steeringWheelDirection = Physics.normalise(-MAXIMUM_STEER_ANGLE, MAXIMUM_STEER_ANGLE, vehicleUpdateDto.getSteeringWheelOrientation());
            acceleratorPedalDepth = Physics.normalise(0d, 100d, vehicleUpdateDto.getAcceleratorPedalDepth());
            brakePedalDepth = Physics.normalise(0d, 100d, vehicleUpdateDto.getBrakePedalDepth());
            if (vehicleUpdateDto.getGear() < 0) {
                gear = 0;
            } else if (vehicleUpdateDto.getGear() > 5) {
                gear = 5;
            } else {
                gear = vehicleUpdateDto.getGear();
            }
            setFrontSlip(vehicleUpdateDto.isFrontSlip());
            setRearSlip(vehicleUpdateDto.isRearSlip());
        } else {
            steeringWheelDirection = 0;
            acceleratorPedalDepth = 0;
            brakePedalDepth = 0;
        }
        return this;
    }

    protected void applyCollision(Vector2D velocityAddition, double angularVelocityAddition) {
        Vector2D currentVelocity = getVelocity();
        Vector2D newVelocity = currentVelocity.add(velocityAddition);

        double currentAngularVelocity = angularVelocity;
        double newAngularVelocity = currentAngularVelocity + angularVelocityAddition;

        setWRXVelocity(newVelocity.getX());
        setWRYVelocity(newVelocity.getY());

        setAngularVelocity(newAngularVelocity);
    }

    protected void applyCollision(Vector2D velocityAddition) {
        Vector2D currentVelocity = getVelocity();
        Vector2D newVelocity = currentVelocity.add(velocityAddition);
        setWRXVelocity(newVelocity.getX());
        setWRYVelocity(newVelocity.getY());
//        System.out.println("vel: " + getWRXVelocity() + ", " + getWRYVelocity());
    }

    /**
     * This method nudges the vehicle in the direction of the collision normal
     *
     * @param normal
     */
    protected void nudge(Vector2D normal) {
        Vector2D location = new Vector2D(this.location.getX(), this.location.getY());
        Vector2D newLocation = location.add(Physics.getUnitVector(normal));
        this.location.setX(newLocation.getX());
        this.location.setY(newLocation.getY());
        System.out.println("Old location: " + location.getX() + ", " + location.getY() + ". New location: " + newLocation.getX() + ", " + newLocation.getY());
    }

    public double getMaxEngineTorque(double RPM) {
        //TODO: Find out how to make a graph into a RPM to torque conversion.
        return 450d;
    }

    public double getSteerAngleInRadians() {
        return Math.toRadians(steeringWheelDirection);
    }

    protected boolean isSolid() {
        return true;
    }

    protected Vector2D getCentreMass() {
        //TODO: is there a better way to find this?
        List<Vertex> vertices = getRectangleVertices();
        Vector2D point0 = vertices.get(0).asVector2D();
        Vector2D point1 = vertices.get(1).asVector2D();
        Vector2D point2 = vertices.get(2).asVector2D();
        Vector2D point3 = vertices.get(3).asVector2D();

        Vector2D total = point0.add(point1).add(point2).add(point3);

        return total.divide(4);
    }

    protected double getCoefficientOfResitution() {
        return this.coefficientOfResitution;
    }

    public void setCoefficientOfResitution(double coefficientOfResitution) {
        this.coefficientOfResitution = coefficientOfResitution;
    }

    public double getGearRatio(int gearNumber) {
        return this.gearRatios[gearNumber];
    }

    public double getSin() {
        return Math.sin(directionOfTravel);
    }

    public double getCos() {
        return Math.cos(directionOfTravel);
    }

    public Vector2D getVehicleReferenceVelocity() {
        Vector2D vehicleReferenceVelocity = new Vector2D();
        vehicleReferenceVelocity.setX(getCos() * worldReferenceYVelocity + getSin() * worldReferenceXVelocity);
        vehicleReferenceVelocity.setY(getCos() * worldReferenceXVelocity - getSin() * worldReferenceYVelocity);
        return vehicleReferenceVelocity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Vector2D getLocation() {
        return location;
    }

    public void setLocation(Vector2D location) {
        this.location = location;
    }

    public double getDirectionOfTravel() {
        return directionOfTravel;
    }

    public void setDirectionOfTravel(double directionOfTravel) {
        this.directionOfTravel = Physics.normaliseBearing(directionOfTravel);
    }

    public double getOppositeDirectionOfTravel() {
        double angle = directionOfTravel + Math.PI;
        return Physics.normaliseBearing(angle);
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getWeight() {
        return mass * 9.81d;
    }

    public double getSteeringWheelDirection() {
        return steeringWheelDirection;
    }

    public void setSteeringWheelDirection(double steeringWheelDirection) {
        this.steeringWheelDirection = steeringWheelDirection;
    }

    public void steerRight() {
        steeringWheelDirection = MAXIMUM_STEER_ANGLE;
    }

    public void steerLeft() {
        steeringWheelDirection = -MAXIMUM_STEER_ANGLE;
    }

    public void normaliseSteerAngle() {
        steeringWheelDirection = 0;
    }

    public double getAcceleratorPedalDepth() {
        return acceleratorPedalDepth;
    }

    public void setAcceleratorPedalDepth(double acceleratorPedalDepth) {
        this.acceleratorPedalDepth = acceleratorPedalDepth;
    }

    public double getBrakePedalDepth() {
        return brakePedalDepth;
    }

    public void setBrakePedalDepth(double brakePedalDepth) {
        this.brakePedalDepth = brakePedalDepth;
    }

    public int getGear() {
        return gear;
    }

    public void setGear(int gear) {
        this.gear = gear;
    }

    public double getAngularVelocity() {
        return angularVelocity;
    }

    public void setAngularVelocity(double angularVelocity) {
        this.angularVelocity = angularVelocity;
    }

    public double getWRXVelocity() {
        return worldReferenceXVelocity;
    }

    public void setWRXVelocity(double xVelocity) {
        this.worldReferenceXVelocity = xVelocity;
    }

    public double getMaxEngineForce() {
        return maxEngineForce;
    }

    public void setMaxEngineForce(double maxEngineForce) {
        this.maxEngineForce = maxEngineForce;
    }

    public double getWRYVelocity() {
        return worldReferenceYVelocity;
    }

    public void setWRYVelocity(double yVelocity) {
        this.worldReferenceYVelocity = yVelocity;
    }

    public double getMaxBrakingForce() {
        return maxBrakingForce;
    }

    public void setMaxBrakingForce(double maxBrakingForce) {
        this.maxBrakingForce = maxBrakingForce;
    }

    public double getDragConstant() {
        return dragConstant;
    }

    public void setDragConstant(double dragConstant) {
        this.dragConstant = dragConstant;
    }

    public double getRollingResistanceConstant() {
        return rollingResistanceConstant;
    }

    public void setRollingResistanceConstant(double rollingResistanceConstant) {
        this.rollingResistanceConstant = rollingResistanceConstant;
    }

    public double getWheelBaseConstant() {
        return wheelBaseConstant;
    }

    public void setWheelBaseConstant(double wheelBaseConstant) {
        this.wheelBaseConstant = wheelBaseConstant;
    }

    public double getCentreOfGravityHeight() {
        return centreOfGravityHeight;
    }

    public void setCentreOfGravityHeight(double centreOfGravityHeight) {
        this.centreOfGravityHeight = centreOfGravityHeight;
    }

    public double getDifferentialRatio() {
        return differentialRatio;
    }

    public void setDifferentialRatio(double differentialRatio) {
        this.differentialRatio = differentialRatio;
    }

    public double getTransmissionEfficiency() {
        return transmissionEfficiency;
    }

    public void setTransmissionEfficiency(double transmissionEfficiency) {
        this.transmissionEfficiency = transmissionEfficiency;
    }

    public double getWheelRadius() {
        return wheelRadius;
    }

    public void setWheelRadius(double wheelRadius) {
        this.wheelRadius = wheelRadius;
    }

    public double[] getGearRatios() {
        return gearRatios;
    }

    public void setGearRatios(double[] gearRatios) {
        this.gearRatios = gearRatios;
    }

    public double getInertia() {
        return inertia;
    }

    public void setInertia(double inertia) {
        this.inertia = inertia;
    }

    public double getFrontWheelBase() {
        return frontWheelBase;
    }

    public void setFrontWheelBase(double frontWheelBase) {
        this.frontWheelBase = frontWheelBase;
    }

    public double getRearWheelBase() {
        return rearWheelBase;
    }

    public void setRearWheelBase(double rearWheelBase) {
        this.rearWheelBase = rearWheelBase;
    }

    public double getAngularAcceleration() {
        return angularAcceleration;
    }

    public void setAngularAcceleration(double angularAcceleration) {
        this.angularAcceleration = angularAcceleration;
    }

    public double getTorque() {
        return torque;
    }

    public void setTorque(double torque) {
        this.torque = torque;
    }

    public double getWorldReferenceXAcceleration() {
        return worldReferenceXAcceleration;
    }

    public void setWorldReferenceXAcceleration(double worldReferenceXAcceleration) {
        this.worldReferenceXAcceleration = worldReferenceXAcceleration;
    }

    public double getWorldReferenceYAcceleration() {
        return worldReferenceYAcceleration;
    }

    public void setWorldReferenceYAcceleration(double worldReferenceYAcceleration) {
        this.worldReferenceYAcceleration = worldReferenceYAcceleration;
    }

    public double getVehicleReferenceXAcceleration() {
        return vehicleReferenceXAcceleration;
    }

    public void setVehicleReferenceXAcceleration(double vehicleReferenceXAcceleration) {
        this.vehicleReferenceXAcceleration = vehicleReferenceXAcceleration;
    }

    public double getVehicleReferenceYAcceleration() {
        return vehicleReferenceYAcceleration;
    }

    public void setVehicleReferenceYAcceleration(double vehicleReferenceYAcceleration) {
        this.vehicleReferenceYAcceleration = vehicleReferenceYAcceleration;
    }

    public double getSpeed() {
        return Math.sqrt(worldReferenceXVelocity * worldReferenceXVelocity + worldReferenceYVelocity * worldReferenceYVelocity);
    }

    public boolean isRearSlip() {
        return rearSlip;
    }

    public void setRearSlip(boolean rearSlip) {
        this.rearSlip = rearSlip;
    }

    public boolean isFrontSlip() {
        return frontSlip;
    }

    public void setFrontSlip(boolean frontSlip) {
        this.frontSlip = frontSlip;
    }

    public boolean isComputer() {
        return isComputer;
    }

    public void setComputer(boolean computer) {
        isComputer = computer;
    }

    public double getX() {
        return location.getX();
    }

    public double getY() {
        return location.getY();
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public boolean isMovable() {
        return true;
    }

    public double getRotation() {
        return directionOfTravel;
    }

    public Vector2D getVelocity() {
        return new Vector2D(worldReferenceXVelocity, worldReferenceYVelocity);
    }

    public int getWaypointNumber() {
        return waypointNumber;
    }

    public void setWaypointNumber(int waypointNumber) {
        this.waypointNumber = waypointNumber;
    }

    public int getLap() {
        return lap;
    }

    public void setLap(int lap) {
        this.lap = lap;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getRpm() {
        return rpm;
    }

    public void setRpm(int rpm) {
        this.rpm = rpm;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public static List<Vehicle> getVehicles(List<VehicleCreationDto> vehiclesToCreate) {
        List<Vehicle> vehicles = new LinkedList<>();
        int count = 0;
        for (VehicleCreationDto vehicleCreationDto : vehiclesToCreate) {
            vehicles.add(getVehicleById(vehicleCreationDto.getVehicleType(), vehicleCreationDto.getComputer(), count));
            count++;
        }
        return vehicles;
    }

    private static Vehicle getVehicleById(VehicleType vehicleType, boolean isComputer, int id) {
        switch (vehicleType) {
            case CAR:
                switch (id) {
                    //TODO: change this back
                    case 0:
                        return new Car(id, 150, 225, Math.PI, isComputer);
                    case 1:
                        return new Car(id, 200, 240, Math.PI, isComputer);
                    case 2:
                        return new Car(id, 150, 255, Math.PI, isComputer);
                    case 3:
                        return new Car(id, 200, 270, Math.PI, isComputer);
                    case 4:
                        return new Car(id, 150, 285, Math.PI, isComputer);
                    case 5:
                        return new Car(id, 200, 300, Math.PI, isComputer);
                }
        }
        return null;
    }
}
