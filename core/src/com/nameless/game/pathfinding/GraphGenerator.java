package com.nameless.game.pathfinding;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Array;
import com.nameless.game.maps.LevelManager;


public class GraphGenerator {

    public static GraphImp generateGraph(TiledMap map) {
        Array<Node> nodes = new Array<Node>();

        TiledMapTileLayer tiles = (TiledMapTileLayer)map.getLayers().get("NodesUnwalkable");
        int mapHeight = LevelManager.HEIGHT_IN_TILES;
        int mapWidth = LevelManager.WIDTH_IN_TILES;

        // Loops over the tiles in the map, starting from bottom left corner
        // iterating left to right, then down to up
        for (int y = 0; y < mapHeight; ++y) {
            for (int x = 0; x < mapWidth; ++x) {
                // generate a node for each tile so that they all exist when we create connections
                Node node = new Node();
                node.type = Node.Type.REGULAR;
                nodes.add(node);
            }
        }

        for (int y = 0; y < mapHeight; ++y) {
            for (int x = 0; x < mapWidth; ++x) {
                TiledMapTileLayer.Cell target = tiles.getCell(x, y);
                TiledMapTileLayer.Cell up = tiles.getCell(x, y+1);
                TiledMapTileLayer.Cell upLeft = tiles.getCell(x-1, y+1);
                TiledMapTileLayer.Cell upRight = tiles.getCell(x+1, y+1);
                TiledMapTileLayer.Cell left = tiles.getCell(x-1, y);
                TiledMapTileLayer.Cell right = tiles.getCell(x+1, y);
                TiledMapTileLayer.Cell down = tiles.getCell(x, y-1);
                TiledMapTileLayer.Cell downLeft = tiles.getCell(x-1, y-1);
                TiledMapTileLayer.Cell downRight = tiles.getCell(x+1, y-1);


                Node targetNode = nodes.get(mapWidth * y + x);
                if (target == null) {
                    if (y != 0 && down == null) {
                        Node downNode = nodes.get(mapWidth * (y - 1) + x);
                        targetNode.createConnection(downNode, 10, NodePosFromOtherNode.DOWN);
                    }
                    if (x != 0 && y != 0 && downLeft == null) {
                        Node downLeftNode = nodes.get(mapWidth * (y - 1) + (x - 1));
                        //targetNode.createConnection(downLeftNode, 14, NodePosFromOtherNode.DOWNLEFT);
                    }
                    if (x != mapWidth - 1 && y != 0 && downRight == null) {
                        Node downRightNode = nodes.get(mapWidth * (y - 1) + (x + 1));
                        //targetNode.createConnection(downRightNode, 14, NodePosFromOtherNode.DOWNRIGHT);
                    }
                    if (x != 0 && left == null) {
                        Node leftNode = nodes.get(mapWidth * y + x - 1);
                        targetNode.createConnection(leftNode, 10, NodePosFromOtherNode.LEFT);
                    }
                    if (x != mapWidth - 1 && right == null) {
                        Node rightNode = nodes.get(mapWidth * y + x + 1);
                        targetNode.createConnection(rightNode, 10, NodePosFromOtherNode.RIGHT);
                    }
                    if (y != mapHeight - 1 && up == null) {
                        Node upNode = nodes.get(mapWidth * (y + 1) + x);
                        targetNode.createConnection(upNode, 10, NodePosFromOtherNode.UP);
                    }
                    if (x != 0 && y != mapHeight - 1 && upLeft == null) {
                        Node upLeftNode = nodes.get(mapWidth * (y + 1) + (x - 1));
                        //targetNode.createConnection(upLeftNode, 14, NodePosFromOtherNode.UPLEFT);
                    }
                    if (x != mapWidth - 1 && y != mapHeight - 1 && upRight == null) {
                        Node upRightNode = nodes.get(mapWidth * (y + 1) + (x + 1));
                        //targetNode.createConnection(upRightNode, 14, NodePosFromOtherNode.UPRIGHT);
                    }
                }
            }
        }

        return new GraphImp(nodes);
    }
}