package com.nameless.game.pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;


public class ConnectionImp implements Connection<Node> {
    private Node toNode;
    private Node fromNode;
    private float cost;
    private NodePosFromOtherNode tag;

    public ConnectionImp(Node fromNode, Node toNode, float cost, NodePosFromOtherNode tag) {
        this.fromNode = fromNode;
        this.toNode = toNode;
        this.cost = cost;
        this.tag = tag;
    }

    public NodePosFromOtherNode getTag() {
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