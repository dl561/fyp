package com.dl561.simulation.physics;

import com.dl561.simulation.vehicle.Vertex;

import java.util.LinkedList;
import java.util.List;

public abstract class Collidable {

    protected abstract boolean isMovable();

    protected abstract boolean isSolid();

    protected abstract int getId();

    protected abstract Vector2D getCentreMass();

    protected abstract double getCoefficientOfResitution();

    protected abstract double getMass();

    protected abstract double getInertia();

    protected abstract void applyCollision(Vector2D velocityAddition, double angularVelocityAddition);

    protected abstract void applyCollision(Vector2D velocityAddition);

    protected abstract void nudge(Vector2D normal);

    public Vector2D getNormalVector1() {
        List<Vertex> vertices = getRectangleVertices();
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

    public Vector2D getNormalVector2() {
        List<Vertex> vertices = getRectangleVertices();
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

    public Edge getEdge(int vertex1Id, int vertex2Id) {
        List<Vertex> vertices = getRectangleVertices();
        Vector2D normal;

        Vertex a = new Vertex();
        Vertex d = new Vertex();
        for (Vertex vertex : vertices) {
            if (vertex.getId() == vertex1Id) {
                a = vertex;
            } else if (vertex.getId() == vertex2Id) {
                d = vertex;
            }
        }
        double dx = d.getX() - a.getX();
        double dy = d.getY() - a.getY();
        normal = new Vector2D(-dy, dx);
        return new Edge(normal.getMagnitude(), normal);
    }

    public List<Edge> getEdges() {
        List<Edge> edges = new LinkedList<>();
        edges.add(getEdge(0, 1));
        edges.add(getEdge(1, 2));
        edges.add(getEdge(2, 3));
        edges.add(getEdge(3, 0));
        return edges;
    }

    public List<Vertex> getRectangleVertices() {
        List<Vertex> vertices = new LinkedList<>();
        Vector2D center = new Vector2D(getX() + (getWidth() / 2), getY() + (getLength() / 2));
        List<Vector2D> preRotationVertices = new LinkedList<>();
        //TODO: check whether this is still true, taking a to be top left is from the front end rather
        //than the back end.
        //Upper left hand corner before rotation
        Vector2D a = new Vector2D(getX(), getY());
        //Upper right hand corner
        Vector2D b = new Vector2D(a.getX() + getWidth(), a.getY());
        //Lower right hand corner
        Vector2D c = new Vector2D(a.getX() + getWidth(), a.getY() + getLength());
        //Lower left hand corner
        Vector2D d = new Vector2D(a.getX(), a.getY() + getLength());

        preRotationVertices.add(a);
        preRotationVertices.add(b);
        preRotationVertices.add(c);
        preRotationVertices.add(d);

        int count = 0;
        for (Vector2D vertex : preRotationVertices) {
            Vector2D translated = new Vector2D(vertex.getX() - center.getX(), vertex.getY() - center.getY());
            double rotatedX = translated.getX() * Math.cos(getRotation()) - translated.getY() * Math.sin(getRotation());
            double rotatedY = translated.getX() * Math.sin(getRotation()) + translated.getY() * Math.cos(getRotation());
            Vertex realVertexPosition = new Vertex();
            realVertexPosition.setId(count);
            realVertexPosition.setX(rotatedX + center.getX());
            realVertexPosition.setY(rotatedY + center.getY());
            vertices.add(realVertexPosition);
            count++;
        }
        return vertices;
    }

    protected abstract double getX();

    protected abstract double getY();

    protected abstract double getWidth();

    protected abstract double getLength();

    protected abstract double getRotation();

    protected abstract Vector2D getVelocity();

    protected abstract double getAngularVelocity();
}
