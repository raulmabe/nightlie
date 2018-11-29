package com.nameless.game.pathfinding;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.nameless.game.maps.LevelManager;

import java.util.Iterator;


public class PathfindingDebugger {
    private static OrthographicCamera camera;
    private static ShapeRenderer shapeRenderer;

    public static void setCamera(OrthographicCamera camera) {
        PathfindingDebugger.camera = camera;
        shapeRenderer = new ShapeRenderer();
    }

    public static void drawPoint2Point(Vector2 pos1, Vector2 pos2) {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 1, 1);
        shapeRenderer.circle(pos1.x, pos1.y, 5);
        shapeRenderer.circle(pos2.x, pos2.y, 5);
//        shapeRenderer.line(pos1, pos2, 10);
        shapeRenderer.rectLine(pos1, pos2, 3);
        shapeRenderer.end();
    }

    public static void drawPositionNode(Node n, Color color){
        int index = n.getIndex();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color);
        shapeRenderer.circle(LevelManager.TILE_WIDTH / 2 + (index % LevelManager.WIDTH_IN_TILES) * LevelManager.TILE_WIDTH,
                LevelManager.TILE_HEIGHT / 2 + (index / LevelManager.WIDTH_IN_TILES) * LevelManager.TILE_HEIGHT, .5f);
        shapeRenderer.end();
    }

    public static void drawPath(GraphPathImp path) {
        Iterator<Node> pathIterator = path.iterator();
        Node priorNode = null;

        while (pathIterator.hasNext()) {
            Node node = pathIterator.next();

            int index = node.getIndex();

            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1, 0, 0, .3f);
            shapeRenderer.circle(LevelManager.TILE_WIDTH / 2 + (index % LevelManager.WIDTH_IN_TILES) * LevelManager.TILE_WIDTH,
                    LevelManager.TILE_HEIGHT / 2 + (index / LevelManager.WIDTH_IN_TILES) * LevelManager.TILE_HEIGHT, .5f);
            shapeRenderer.end();

            if (priorNode != null) {
                int index2 = priorNode.getIndex();

                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.line(LevelManager.TILE_WIDTH / 2 + (index % LevelManager.WIDTH_IN_TILES) * LevelManager.TILE_WIDTH,
                        LevelManager.TILE_HEIGHT / 2 + (index / LevelManager.WIDTH_IN_TILES) * LevelManager.TILE_HEIGHT,
                        LevelManager.TILE_WIDTH / 2 + (index2 % LevelManager.WIDTH_IN_TILES) * LevelManager.TILE_WIDTH,
                        LevelManager.TILE_HEIGHT / 2 + (index2 / LevelManager.WIDTH_IN_TILES) * LevelManager.TILE_HEIGHT);
                shapeRenderer.end();
            }

            priorNode = node;
        }
    }
}