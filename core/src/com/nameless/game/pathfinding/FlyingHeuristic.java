package com.nameless.game.pathfinding;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.nameless.game.maps.LevelManager;


public class FlyingHeuristic implements Heuristic<Node> {
    @Override
    public float estimate(Node startNode, Node endNode) {
        int startIndex = startNode.getIndex();
        int endIndex = endNode.getIndex();

        int startY = (int) (startIndex / LevelManager.TILE_WIDTH);
        int startX = (int) (startIndex % LevelManager.TILE_WIDTH);

        int endY = (int) (endIndex / LevelManager.TILE_WIDTH);
        int endX = (int) (endIndex % LevelManager.TILE_WIDTH);

        // Pythagorean distance
        float distance = (float) Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));

        return distance;
    }
}