package com.nameless.game.flowfield;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Colors;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.nameless.game.Constants;
import com.nameless.game.maps.LevelManager;
import com.nameless.game.pathfinding.Node;
import com.nameless.game.pathfinding.PathfindingDebugger;

public class FlowFieldDebugger {

    private static OrthographicCamera camera;
    private static ShapeRenderer shapeRenderer;

    private static BitmapFont font = new BitmapFont(); //or use alex answer to use custom font


    public static void setCamera(OrthographicCamera camera) {
        FlowFieldDebugger.camera = camera;
        shapeRenderer = new ShapeRenderer();
    }

    public static void drawDistances(Batch batch){
        batch.begin();
        font.getData().setScale(.5f);

        //draw background, objects, etc.
        for (int x = 0; x < LevelManager.WIDTH_IN_TILES * LevelManager.TILE_WIDTH; x +=  LevelManager.TILE_WIDTH){
            for (int y = 0; y < LevelManager.HEIGHT_IN_TILES* LevelManager.TILE_HEIGHT; y +=  LevelManager.TILE_HEIGHT){
                if(LevelManager.graph.getNodeByXY(x ,y).getDistance() < 0) font.setColor(Color.RED);
                else font.setColor(Color.WHITE);
                font.draw(batch, String.valueOf(LevelManager.graph.getNodeByXY(x ,y).getDistance()),
                        x + LevelManager.TILE_WIDTH/2, y + LevelManager.TILE_HEIGHT/2);
            }
        }

        font.draw(batch, "Hello World!", -1, -1);

        batch.end();
    }

    public static void drawFlow(){
        shapeRenderer.setProjectionMatrix(camera.combined);
        /*
        int z = 3;
        // Draws each sector differently
        for (int x = 0; x < LevelManager.WIDTH_IN_TILES ; x++) {
            for (int y = 0; y < LevelManager.HEIGHT_IN_TILES; y++) {
                switch(y / (LevelManager.HEIGHT_IN_TILES/z) + (x / (LevelManager.WIDTH_IN_TILES/z)) * z ){
                    case 0:
                        PathfindingDebugger.drawPositionNode(LevelManager.graph.getNodeByXYTiles(x,y), Color.YELLOW);
                        break;
                    case 1:
                        PathfindingDebugger.drawPositionNode(LevelManager.graph.getNodeByXYTiles(x,y), Color.ORANGE);
                        break;
                    case 2:
                        PathfindingDebugger.drawPositionNode(LevelManager.graph.getNodeByXYTiles(x,y), Color.RED);
                        break;
                    case 3:
                        PathfindingDebugger.drawPositionNode(LevelManager.graph.getNodeByXYTiles(x,y), Color.MAGENTA);
                        break;
                    case 4:
                        PathfindingDebugger.drawPositionNode(LevelManager.graph.getNodeByXYTiles(x,y), Color.CORAL);
                        break;
                    case 5:
                        PathfindingDebugger.drawPositionNode(LevelManager.graph.getNodeByXYTiles(x,y), Color.BLUE);
                        break;
                    case 6:
                        PathfindingDebugger.drawPositionNode(LevelManager.graph.getNodeByXYTiles(x,y), Color.CYAN);
                        break;
                    case 7:
                        PathfindingDebugger.drawPositionNode(LevelManager.graph.getNodeByXYTiles(x,y), Color.GREEN);
                        break;
                    case 8:
                        PathfindingDebugger.drawPositionNode(LevelManager.graph.getNodeByXYTiles(x,y), Color.LIME);
                        break;
                    default:
                        PathfindingDebugger.drawPositionNode(LevelManager.graph.getNodeByXYTiles(x,y), Color.BLACK);

                }
            }
        }*/

         // Draws every nodes
        for (Node node :  LevelManager.graph.nodes) {
            if(!node.flow.isZero()) PathfindingDebugger.drawPositionNode(node, Color.DARK_GRAY);
        }

        // Draws only nodes updating
        for (int x = FlowFieldManager.getMinX(); x <= FlowFieldManager.getMaxX() ; x++) {
            for (int y = FlowFieldManager.getMinY(); y <= FlowFieldManager.getMaxY(); y++) {
                if(!LevelManager.graph.getNodeByXYTiles(x,y).flow.isZero())
                    PathfindingDebugger.drawPositionNode(LevelManager.graph.getNodeByXYTiles(x,y), Color.BLACK);
            }
        }

        // Draw player node
        PathfindingDebugger.drawPositionNode(LevelManager.graph.getNodeByXYTiles(FlowFieldManager.nodeGoalX,FlowFieldManager.nodeGoalY),
                Color.GREEN);

        // Draw vector pointing at player
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);

        for (int x = FlowFieldManager.getMinX(); x <= FlowFieldManager.getMaxX() ; x++) {
            for (int y = FlowFieldManager.getMinY(); y <= FlowFieldManager.getMaxY(); y++) {
                if(!LevelManager.graph.getNodeByXYTiles(x,y).flow.isZero()) {
                    shapeRenderer.line(x * LevelManager.TILE_WIDTH + LevelManager.TILE_WIDTH/2,
                            y * LevelManager.TILE_HEIGHT + LevelManager.TILE_HEIGHT/2,
                            x * LevelManager.TILE_WIDTH + LevelManager.TILE_WIDTH/2 + (LevelManager.graph.getNodeByXYTiles(x,y).flow.nor().x/2),
                            y * LevelManager.TILE_HEIGHT + LevelManager.TILE_HEIGHT/2 + (LevelManager.graph.getNodeByXYTiles(x,y).flow.nor().y/2));
                }
            }
        }

        shapeRenderer.end();
    }
}
