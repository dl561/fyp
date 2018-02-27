package com.dl561.rest.domain.map;

import com.dl561.rest.domain.dto.CourseDto;
import com.dl561.rest.domain.dto.RectangleDto;
import com.dl561.rest.domain.dto.SimulationDto;
import com.dl561.rest.domain.dto.VehicleDto;
import com.dl561.simulation.Simulation;
import com.dl561.simulation.course.Course;
import com.dl561.simulation.course.segment.Rectangle;
import com.dl561.simulation.vehicle.Vehicle;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class SimulationMapper {

    public SimulationDto mapToDto(Simulation simulation) {
        SimulationDto dto = new SimulationDto();
        dto.setId(simulation.getId());
        dto.setName(simulation.getName());
        dto.setNumberOfLaps(simulation.getNumberOfLaps());
        dto.setRunning(simulation.getRunning());
        dto.setWaypoints(simulation.getWaypoints());
        dto.setCourse(mapToDto(simulation.getCourse()));
        dto.setVehicles(mapVehicleListToDto(simulation.getVehicles()));
        return dto;
    }

    public CourseDto mapToDto(Course course) {
        CourseDto dto = new CourseDto();
        dto.setRectangles(mapRectangleListToDto(course.getRectangles()));
        dto.setArcs(course.getArcs());
        return dto;
    }

    public VehicleDto mapToDto(Vehicle vehicle) {
        VehicleDto dto = new VehicleDto();
        dto.setId(vehicle.getId());
        dto.setComputer(vehicle.isComputer());
        dto.setPosition(vehicle.getPosition());
        dto.setFinished(vehicle.isFinished());
        dto.setLap(vehicle.getLap());
        dto.setWaypointNumber(vehicle.getWaypointNumber());
        dto.setLocation(vehicle.getLocation());
        dto.setDirectionOfTravel(vehicle.getDirectionOfTravel());
        dto.setAcceleratorPedalDepth(vehicle.getAcceleratorPedalDepth());
        dto.setBrakePedalDepth(vehicle.getBrakePedalDepth());
        dto.setGear(vehicle.getGear());
        dto.setSteeringWheelDirection(vehicle.getSteeringWheelDirection());
        dto.setRpm(vehicle.getRpm());
        dto.setSpeed(vehicle.getSpeed());
        return dto;
    }

    public RectangleDto mapToDto(Rectangle rectangle) {
        RectangleDto dto = new RectangleDto();
        dto.setX(rectangle.getX());
        dto.setY(rectangle.getY());
        dto.setLength(rectangle.getLength());
        dto.setWidth(rectangle.getWidth());
        dto.setRotation(rectangle.getRotation());
        dto.setSolid(rectangle.isSolid());
        dto.setHexColour(rectangle.getHexColour());
        return dto;
    }

    public List<VehicleDto> mapVehicleListToDto(List<Vehicle> vehicleList) {
        List<VehicleDto> dtos = new LinkedList<>();
        for (Vehicle vehicle : vehicleList) {
            dtos.add(mapToDto(vehicle));
        }
        return dtos;
    }

    public List<RectangleDto> mapRectangleListToDto(List<Rectangle> rectangles) {
        List<RectangleDto> dtos = new LinkedList<>();
        for (Rectangle rectangle : rectangles) {
            dtos.add(mapToDto(rectangle));
        }
        return dtos;
    }

    public List<SimulationDto> mapSimulationListToDto(List<Simulation> simulations) {
        List<SimulationDto> dtos = new LinkedList<>();
        for (Simulation simulation : simulations) {
            dtos.add(mapToDto(simulation));
        }
        return dtos;
    }
}
