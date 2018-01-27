package com.dl561.simulation.debug;

import com.dl561.simulation.physics.Vector2D;

public class ReportLine {
    Vector2D velocity;
    double rotationAngle;
    double sideSlip;
    double slipAngleFront;
    double slipAngleRear;
    double frontWheelWeight;
    double rearWheelWeight;
    Vector2D frontWheelLateralForce;
    Vector2D rearWheelLateralForce;
    Vector2D tractionForce;
    Vector2D rollingResistance;
    Vector2D dragResistance;
    Vector2D resistance;
    Vector2D totalForce;
    double torque;
    Vector2D acceleration;
    double angularAcceleration;
    Vector2D worldReferenceAcceleration;
    Vector2D worldReferenceVelocity;
    Vector2D newPosition;

    public ReportLine(Vector2D velocity, double rotationAngle, double sideSlip, double slipAngleFront, double slipAngleRear, double frontWheelWeight, double rearWheelWeight, Vector2D frontWheelLateralForce, Vector2D rearWheelLateralForce, Vector2D tractionForce, Vector2D rollingResistance, Vector2D dragResistance, Vector2D resistance, Vector2D totalForce, double torque, Vector2D acceleration, double angularAcceleration, Vector2D worldReferenceAcceleration, Vector2D worldReferenceVelocity, Vector2D newPosition) {
        this.velocity = velocity;
        this.rotationAngle = rotationAngle;
        this.sideSlip = sideSlip;
        this.slipAngleFront = slipAngleFront;
        this.slipAngleRear = slipAngleRear;
        this.frontWheelWeight = frontWheelWeight;
        this.rearWheelWeight = rearWheelWeight;
        this.frontWheelLateralForce = frontWheelLateralForce;
        this.rearWheelLateralForce = rearWheelLateralForce;
        this.tractionForce = tractionForce;
        this.rollingResistance = rollingResistance;
        this.dragResistance = dragResistance;
        this.resistance = resistance;
        this.totalForce = totalForce;
        this.torque = torque;
        this.acceleration = acceleration;
        this.angularAcceleration = angularAcceleration;
        this.worldReferenceAcceleration = worldReferenceAcceleration;
        this.worldReferenceVelocity = worldReferenceVelocity;
        this.newPosition = newPosition;
    }

    public static String getHeaderLine() {
        return "velocityX," +
                "velocityY," +
                "rotationAngle," +
                "sideSlip," +
                "slipAngleFront," +
                "slipAngleRear," +
                "frontWheelWeight," +
                "rearWheelWeight," +
                "frontWheelLateralForceX," +
                "frontWheelLateralForceY," +
                "rearWheelLateralForceX," +
                "rearWheelLateralForceY," +
                "tractionForceX," +
                "tractionForceY," +
                "rollingResistanceX," +
                "rollingResistanceY," +
                "dragResistanceX," +
                "dragResistanceY," +
                "resistanceX," +
                "resistanceY," +
                "totalForceX," +
                "totalForceY," +
                "torque," +
                "accelerationX," +
                "accelerationY," +
                "angularAcceleration," +
                "WFAcceleration,X" +
                "WFAcceleration,Y" +
                "WRVelocityX," +
                "WRVelocityY," +
                "newPositionX," +
                "newPositionY" + "\n";
    }

    @Override
    public String toString() {
        return velocity.getX() + "," +
                velocity.getY() + "," +
                rotationAngle + "," +
                sideSlip + "," +
                slipAngleFront + "," +
                slipAngleRear + "," +
                frontWheelWeight + "," +
                rearWheelWeight + "," +
                frontWheelLateralForce.getX() + "," +
                frontWheelLateralForce.getY() + "," +
                rearWheelLateralForce.getX() + "," +
                rearWheelLateralForce.getY() + "," +
                tractionForce.getX() + "," +
                tractionForce.getY() + "," +
                rollingResistance.getX() + "," +
                rollingResistance.getY() + "," +
                dragResistance.getX() + "," +
                dragResistance.getY() + "," +
                resistance.getX() + "," +
                resistance.getY() + "," +
                totalForce.getX() + "," +
                totalForce.getY() + "," +
                torque + "," +
                acceleration.getX() + "," +
                acceleration.getY() + "," +
                angularAcceleration + "," +
                worldReferenceAcceleration.getX() + "," +
                worldReferenceAcceleration.getY() + "," +
                worldReferenceVelocity.getX() + "," +
                worldReferenceVelocity.getY() + "," +
                newPosition.getX() + "," +
                newPosition.getY() + "\n";
    }
}
