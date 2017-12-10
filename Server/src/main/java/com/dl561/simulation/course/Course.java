package com.dl561.simulation.course;

import com.dl561.simulation.course.segment.Arc;
import com.dl561.simulation.course.segment.Rectangle;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class Course {
    private List<Rectangle> rectangles;
    private List<Arc> arcs;
    private static final String OBSTACLE_COLOUR = "#FFFF00";
    private static final String ROAD_COLOUR = "#808080";
    private static final String GRASS_COLOUR = "#323232";
    private static final String ICE_COLOUR = "#FFFFFF";
    private static final String SAND_COLOUR = "#000000";

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
        rectangles.add(new Rectangle(1145, 735, 859, 297, 0, GRASS_COLOUR));
        rectangles.add(new Rectangle(0, 0, 1280, 570, 0, GRASS_COLOUR));

        rectangles.add(new Rectangle(950, 330, 920, 80, 0, ROAD_COLOUR));
        rectangles.add(new Rectangle(665, 590, 97, 355, 0, ROAD_COLOUR));
        rectangles.add(new Rectangle(925, 2235, 930, 83, 0, ROAD_COLOUR));
        rectangles.add(new Rectangle(5445, 630, 77, 345, 0, ROAD_COLOUR));


        rectangles.add(new Rectangle(0, 0, 1, 2850, 0, OBSTACLE_COLOUR));
        rectangles.add(new Rectangle(0, 0, 6400, 1, 0, OBSTACLE_COLOUR));
        rectangles.add(new Rectangle(6395, 0, 1, 2850, 0, OBSTACLE_COLOUR));
        rectangles.add(new Rectangle(0, 2845, 6400, 1, 0, OBSTACLE_COLOUR));
        rectangles.add(new Rectangle(1300, 815, 31, 265, 0, OBSTACLE_COLOUR));
        rectangles.add(new Rectangle(1300, 815, 787, 32, 0, OBSTACLE_COLOUR));
        rectangles.add(new Rectangle(5090, 815, 30, 264, 0, OBSTACLE_COLOUR));
        rectangles.add(new Rectangle(1300, 1980, 789, 32, 0, OBSTACLE_COLOUR));


        course.setRectangles(rectangles);

        List<Arc> arcs = new LinkedList<>();
        arcs.add(new Arc(965, 630, 30, Math.PI, 1.5 * Math.PI, false, 0, ROAD_COLOUR));
        arcs.add(new Arc(5530, 630, 30, 1.5 * Math.PI, 0, false, 0, ROAD_COLOUR));
        arcs.add(new Arc(965, 2350, 30, 0.5 * Math.PI, Math.PI, false, 0, ROAD_COLOUR));
        arcs.add(new Arc(5530, 2350, 30, 0, 0.5 * Math.PI, false, 0, ROAD_COLOUR));

        course.setArcs(arcs);

        return course;
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
