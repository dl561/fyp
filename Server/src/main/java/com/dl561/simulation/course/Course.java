package com.dl561.simulation.course;

import com.dl561.rest.domain.dto.VehicleCreationDto;
import com.dl561.simulation.course.segment.Arc;
import com.dl561.simulation.course.segment.Rectangle;
import com.dl561.simulation.vehicle.Car;
import com.dl561.simulation.vehicle.Vehicle;
import com.dl561.simulation.vehicle.VehicleType;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class Course {
    private List<Rectangle> rectangles;
    private List<Arc> arcs;

    public static Course getByTrackNumber(int trackNumber) {
        switch (trackNumber) {
            case 1:
                return getTrack1();
        }
        return null;
    }

    private static Course getTrack1() {
        Course course = new Course();

        List<Rectangle> rectangles = new LinkedList<>();
        /**
        rectangles.add(new Rectangle(150, 50, 900, 100, 0));
        rectangles.add(new Rectangle(150, 420, 900, 100, 0));
        rectangles.add(new Rectangle(100, 100, 100, 370, 0));
        rectangles.add(new Rectangle(1000, 100, 100, 370, 0));
         **/
        course.setRectangles(rectangles);

        List<Arc> arcs = new LinkedList<>();
        /**
        arcs.add(new Arc(160, 110, 30, Math.toRadians(180), Math.toRadians(270), false, 0));
        arcs.add(new Arc(1040, 110, 30, Math.toRadians(270), Math.toRadians(0), false, 0));
        arcs.add(new Arc(160, 460, 30, Math.toRadians(90), Math.toRadians(180), false, 0));
        arcs.add(new Arc(1040, 460, 30, Math.toRadians(180), Math.toRadians(90), false, 0));
         **/
        course.setArcs(arcs);

        return course;
    }

    public static List<Vehicle> getVehiclesByTrackNumber(int trackNumber, List<VehicleCreationDto> vehiclesToCreate) {
        int count = 0;
        List<Vehicle> vehicles = new LinkedList<>();
        for (VehicleCreationDto vehicleCreationDto : vehiclesToCreate) {
            vehicles.add(getVehicleByTrackNumberAndCount(trackNumber, vehicleCreationDto.getVehicleType(), count));
        }
        return vehicles;
    }

    private static Vehicle getVehicleByTrackNumberAndCount(int trackNumber, VehicleType vehicleType, int count) {
        switch (trackNumber) {
            case 1:
                return getVehicleByTypeAndCountOfTrack1(vehicleType, count);
            default:
                return getVehicleByTypeAndCountOfTrack1(vehicleType, count);
        }
    }

    private static Vehicle getVehicleByTypeAndCountOfTrack1(VehicleType vehicleType, int count) {
        switch (vehicleType) {
            case CAR:
                switch (count) {
                    case 0:
                        return new Car(650, 100, 0);
                    case 1:
                        return new Car(610, 55, 90);
                    case 2:
                        return new Car(570, 100, 90);
                    case 3:
                        return new Car(530, 55, 90);
                    case 4:
                        return new Car(490, 100, 90);
                    case 5:
                        return new Car(450, 55, 90);
                    case 6:
                        return new Car(410, 100, 90);
                    case 7:
                        return new Car(370, 55, 90);
                    case 8:
                        return new Car(330, 100, 90);
                    case 9:
                        return new Car(290, 55, 90);
                }
        }
        return null;
    }

    public List<Rectangle> getRectangles() {
        return rectangles;
    }

    public void setRectangles(List<Rectangle> rectangles) {
        this.rectangles = rectangles;
    }

    public List<Arc> getArcs() {
        return arcs;
    }

    public void setArcs(List<Arc> arcs) {
        this.arcs = arcs;
    }
}
