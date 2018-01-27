package com.dl561.simulation.vehicle.engine;

import java.util.ArrayList;
import java.util.List;

public class Engine {
    private static List<Node> torqueRPMGraph;

    public static double getTorque(int rpm) {
        if (torqueRPMGraph == null) {
            populateTorqueGraph();
        }
        int minDistance = Integer.MAX_VALUE;
        Node minNode = null;
        for (Node node : torqueRPMGraph) {
            if (node.distanceToPoint(rpm) < minDistance) {
                minDistance = node.distanceToPoint(rpm);
                minNode = node;
            }
        }
        if (minNode == null) {
            //Should never happen as any will have a lower value than max int value.
            return torqueRPMGraph.get(0).getY();
        }
        return minNode.getY();
    }

    private static void populateTorqueGraph() {
        torqueRPMGraph = new ArrayList<>();
        torqueRPMGraph.add(new Node(1000, 180));
        torqueRPMGraph.add(new Node(1500, 205));
        torqueRPMGraph.add(new Node(2000, 224));
        torqueRPMGraph.add(new Node(2500, 230));
        torqueRPMGraph.add(new Node(3000, 235));
        torqueRPMGraph.add(new Node(3500, 238));
        torqueRPMGraph.add(new Node(4000, 235));
        torqueRPMGraph.add(new Node(4500, 230));
        torqueRPMGraph.add(new Node(5000, 220));
        torqueRPMGraph.add(new Node(5500, 190));
        torqueRPMGraph.add(new Node(6000, 180));
        torqueRPMGraph.add(new Node(6500, 120));
    }
}
