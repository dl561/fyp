package com.dl561.rest.domain.dto;

import com.dl561.simulation.course.segment.Arc;

import java.util.List;

public class CourseDto {
    private List<RectangleDto> rectangles;
    private List<Arc> arcs;

    public List<RectangleDto> getRectangles() {
        return rectangles;
    }

    public void setRectangles(List<RectangleDto> rectangles) {
        this.rectangles = rectangles;
    }

    public List<Arc> getArcs() {
        return arcs;
    }

    public void setArcs(List<Arc> arcs) {
        this.arcs = arcs;
    }
}
