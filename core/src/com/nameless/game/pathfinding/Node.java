package com.nameless.game.pathfinding;


import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.nameless.game.flowfield.FlowFieldManager;

import java.util.ArrayList;
import java.util.List;


public class Node {
    public Array<Connection<Node>> connections = new Array<Connection<Node>>();
    public int type;
    public int index;
    private int x, y;

    private int distance = -1;

    public Vector2 flow;

    public int sector;

    public Node() {
        flow = new Vector2(0,0);
        x = y = -1;
    }

    public Node(int type, int x, int y, int sector) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.sector = sector;
        flow = new Vector2(0,0);
    }

    public int getIndex() {
        return index;
    }

    public Array<Connection<Node>> getConnections() {
        return connections;
    }

    public void createConnection(Node toNode, float cost, Node.Relative tag) {
        connections.add(new ConnectionImp(this, toNode, cost, tag));
    }

    public void removeConnection(int i) {
        connections.removeIndex(i);
    }

    public void calcFlow(){
        int distanceUp, distanceDown, distanceRight, distanceLeft;
        distanceDown = distanceLeft = distanceRight = distanceUp = distance;

        for (Connection<Node> connection: connections) {
            if(connection instanceof  ConnectionImp && connection.getToNode().type == Type.REGULAR){
                if(((ConnectionImp) connection).getTag() == Node.Relative.DOWN) distanceDown = connection.getToNode().getDistance();
                else if(((ConnectionImp) connection).getTag() == Node.Relative.UP) distanceUp = connection.getToNode().getDistance();
                else if(((ConnectionImp) connection).getTag() == Node.Relative.RIGHT) distanceRight = connection.getToNode().getDistance();
                else if(((ConnectionImp) connection).getTag() == Node.Relative.LEFT) distanceLeft = connection.getToNode().getDistance();
            }

        }

        flow.x = distanceLeft - distanceRight;
        flow.y = distanceDown - distanceUp;
    }

    public void setDistance(int dist){
        distance = dist;
    }

    public int getDistance(){
        return distance;
    }

    public int getX(){return x;}

    public int getY(){return y;}

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Node)) return false;

        Node o = (Node) obj;
        return index == o.index;
    }

    public void calcSectorFlow() {
        int distanceUp, distanceDown, distanceRight, distanceLeft;
        distanceDown = distanceLeft = distanceRight = distanceUp = distance;

        for (Connection<Node> connection: connections) {
            if(connection instanceof  ConnectionImp && connection.getToNode().type == Type.REGULAR &&
                    connection.getToNode().sector == FlowFieldManager.activeSector){
                if(((ConnectionImp) connection).getTag() == Node.Relative.DOWN) distanceDown = connection.getToNode().getDistance();
                else if(((ConnectionImp) connection).getTag() == Node.Relative.UP) distanceUp = connection.getToNode().getDistance();
                else if(((ConnectionImp) connection).getTag() == Node.Relative.RIGHT) distanceRight = connection.getToNode().getDistance();
                else if(((ConnectionImp) connection).getTag() == Node.Relative.LEFT) distanceLeft = connection.getToNode().getDistance();
            }

        }

        flow.x = distanceLeft - distanceRight;
        flow.y = distanceDown - distanceUp;
    }

    public static class Type {
        public static final int REGULAR = 1;
        public static final int UNWALKABLE = 2;
    }
    
    public enum Relative{
        UP,RIGHT,DOWN,LEFT
    }
}