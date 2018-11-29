package com.nameless.game.pathfinding;


import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;

import java.util.ArrayList;
import java.util.List;


public class Node {
    public Array<Connection<Node>> connections = new Array<Connection<Node>>();
    public int type;
    public int index;

    private int distance = -1;

    private boolean AmIGoal = false;

    public Vector2 flow = new Vector2();

    public Node() {
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Node)) return false;

        Node o = (Node) obj;
        return index == o.index;
    }

    public Node(int type) {
        this.type = type;
    }

    public int getIndex() {
        return index;
    }

    public Array<Connection<Node>> getConnections() {
        return connections;
    }

    public void createConnection(Node toNode, float cost, NodePosFromOtherNode tag) {
        connections.add(new ConnectionImp(this, toNode, cost, tag));
    }

    public void removeConnection(int i) {
        connections.removeIndex(i);
    }

    public void calcFlow(){
        int distanceUp, distanceDown, distanceRight, distanceLeft;
        distanceDown = distanceLeft = distanceRight = distanceUp = distance;

        for (Connection<Node> connection: connections) {
            if(connection instanceof  ConnectionImp){
                if(((ConnectionImp) connection).getTag() == NodePosFromOtherNode.DOWN) distanceDown = connection.getToNode().getDistance();
                else if(((ConnectionImp) connection).getTag() == NodePosFromOtherNode.UP) distanceUp = connection.getToNode().getDistance();
                else if(((ConnectionImp) connection).getTag() == NodePosFromOtherNode.RIGHT) distanceRight = connection.getToNode().getDistance();
                else if(((ConnectionImp) connection).getTag() == NodePosFromOtherNode.LEFT) distanceLeft = connection.getToNode().getDistance();
            }
        }

        flow.y = distanceUp - distanceDown;
        flow.x = distanceLeft - distanceRight;
    }

    public Vector2 getFlow(){
        if (flow.isZero() || flow == null) calcFlow();
        return flow;
    }

    public void setDistance(int dist){
        distance = dist;
    }

    public int getDistance(){
        return distance;
    }

    public static class Type {
        public static final int REGULAR = 1;
        public static final int NULL = 2;
    }
}