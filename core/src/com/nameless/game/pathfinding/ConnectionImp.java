package com.nameless.game.pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;


public class ConnectionImp implements Connection<Node> {
    private Node toNode;
    private Node fromNode;
    private float cost;
    private Node.Relative tag;

    public ConnectionImp(Node fromNode, Node toNode, float cost, Node.Relative tag) {
        this.fromNode = fromNode;
        this.toNode = toNode;
        this.cost = cost;
        this.tag = tag;
    }

    public Node.Relative getTag() {
        return tag;
    }

    @Override
    public float getCost() {
        return cost;
    }

    @Override
    public Node getFromNode() {
        return fromNode;
    }

    @Override
    public Node getToNode() {
        return toNode;
    }
}