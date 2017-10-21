package com.dl561.simulation.course;

import com.dl561.simulation.course.segment.Arc;
import com.dl561.simulation.course.segment.Rectangle;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Course {
    private List<Rectangle> rectangles;
    private List<Arc> arcs;

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
