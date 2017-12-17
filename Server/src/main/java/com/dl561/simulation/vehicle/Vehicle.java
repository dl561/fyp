package com.dl561.simulation.vehicle;

import com.dl561.rest.domain.dto.VehicleCreationDto;
import com.dl561.rest.domain.dto.VehicleUpdateDto;
import com.dl561.simulation.course.location.Location;
import com.dl561.simulation.physics.Physics;
import com.dl561.simulation.physics.Vector2D;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public abstract class Vehicle {
    private int id;
    //variable
    private Location location;
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
    private double width;
    private double length;
    //constant
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

    public Vehicle update(VehicleUpdateDto vehicleUpdateDto) {
        steeringWheelDirection = Physics.normalise(-45d, 45d, vehicleUpdateDto.getSteeringWheelOrientation());
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
        return this;
    }

    public double getMaxEngineTorque(double RPM) {
        //TODO: Find out how to make a graph into a RPM to torque conversion.
        return 450d;
    }


    /**
     * This method returns a list of unit vectors for Normals of the vehicle
     * Though there are 4 normals, only 2 of them are needed (X and Y of vehicle)
     *
     * @return
     */
    public Vector2D getNormalUnitVector1() {
        List<Vertex> vertices = getVertices();
        Vector2D n1;

        Vertex a = new Vertex();
        Vertex b = new Vertex();
        for (Vertex vertex : vertices) {
            if (vertex.getId() == 0) {
                a = vertex;
            } else if (vertex.getId() == 1) {
                b = vertex;
            }
        }
        double dx = b.getX() - a.getX();
        double dy = b.getY() - a.getY();
        n1 = new Vector2D(-dy, dx);
        return n1;
    }

    public Vector2D getNormalUnitVector2() {
        List<Vertex> vertices = getVertices();
        Vector2D n2;

        Vertex a = new Vertex();
        Vertex d = new Vertex();
        for (Vertex vertex : vertices) {
            if (vertex.getId() == 0) {
                a = vertex;
            } else if (vertex.getId() == 3) {
                d = vertex;
            }
        }
        double dx = d.getX() - a.getX();
        double dy = d.getY() - a.getY();
        n2 = new Vector2D(-dy, dx);
        return n2;
    }

    public List<Vertex> getVertices() {
        List<Vertex> vertices = new LinkedList<>();
        Vector2D center = new Vector2D(location.getX() + (width / 2), location.getY() + (length / 2));
        List<Vector2D> preRotationVertices = new LinkedList<>();
        //TODO: check whether this is still true, taking a to be top left is from the front end rather
        //than the back end.
        //Upper left hand corner before rotation
        Vector2D a = new Vector2D(location.getX(), location.getY());
        //Upper right hand corner
        Vector2D b = new Vector2D(a.getX() + width, a.getY());
        //Lower right hand corner
        Vector2D c = new Vector2D(a.getX() + width, a.getY() + length);
        //Lower left hand corner
        Vector2D d = new Vector2D(a.getX(), a.getY() + length);

        preRotationVertices.add(a);
        preRotationVertices.add(b);
        preRotationVertices.add(c);
        preRotationVertices.add(d);

        int count = 0;
        for (Vector2D vertex : preRotationVertices) {
            Vector2D translated = new Vector2D(vertex.getX() - center.getX(), vertex.getY() - center.getY());
            double rotatedX = translated.getX() * Math.cos(directionOfTravel) - translated.getY() * Math.sin(directionOfTravel);
            double rotatedY = translated.getX() * Math.sin(directionOfTravel) + translated.getY() * Math.cos(directionOfTravel);
            Vertex realVertexPosition = new Vertex();
            realVertexPosition.setId(count);
            realVertexPosition.setX(rotatedX + center.getX());
            realVertexPosition.setY(rotatedY + center.getY());
            vertices.add(realVertexPosition);
            count++;
        }
        return vertices;
    }

    public double getSteerAngleInRadians() {
        return Math.toRadians(steeringWheelDirection);
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
        this.directionOfTravel = directionOfTravel % 360;
    }

    public double getOppositeDirectionOfTravel() {
        return (180 + this.directionOfTravel) % 360;
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

    public static List<Vehicle> getVehicles(List<VehicleCreationDto> vehiclesToCreate) {
        List<Vehicle> vehicles = new LinkedList<>();
        int count = 0;
        for (VehicleCreationDto vehicleCreationDto : vehiclesToCreate) {
            vehicles.add(getVehicleById(vehicleCreationDto.getVehicleType(), vehicleCreationDto.isComputer(), count));
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
                        return new Car(id, 610, 55, 0, isComputer);
                    case 1:
                        return new Car(id, 610, 55, 0, isComputer);
                    case 2:
                        return new Car(id, 570, 100, 0, isComputer);
                    case 3:
                        return new Car(id, 530, 55, 0, isComputer);
                    case 4:
                        return new Car(id, 490, 100, 0, isComputer);
                    case 5:
                        return new Car(id, 450, 55, 0, isComputer);
                    case 6:
                        return new Car(id, 410, 100, 0, isComputer);
                    case 7:
                        return new Car(id, 370, 55, 0, isComputer);
                    case 8:
                        return new Car(id, 330, 100, 0, isComputer);
                    case 9:
                        return new Car(id, 290, 55, 0, isComputer);
                }
        }
        return null;
    }
}
