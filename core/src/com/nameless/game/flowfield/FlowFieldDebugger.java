package com.nameless.game.flowfield;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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


         // Draw points at the center of each node
        for (Node node :  LevelManager.graph.nodes) {
            PathfindingDebugger.drawPositionNode(node, Color.BLACK);
        }
        /*
        for (int i = 0; i < LevelManager.graph.getNodeCount(); ++i) {
            PathfindingDebugger.drawPositionNode(LevelManager.graph.getNode(i), Color.CYAN);
        }

        for (float x = 0; x < LevelManager.WIDTH_IN_TILES * LevelManager.TILE_WIDTH; x +=  LevelManager.TILE_WIDTH * (43/ Constants.PixelsPerMeter)){
            for (float y = 0; y < LevelManager.HEIGHT_IN_TILES* LevelManager.TILE_HEIGHT; y +=  LevelManager.TILE_HEIGHT * (43/ Constants.PixelsPerMeter)){
                System.out.println(x + " " + y );
                PathfindingDebugger.drawPositionNode(LevelManager.graph.getNodeByXYFloat(x,y), Color.GOLD);
            }
        }*/


        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        for (float x = 0; x < LevelManager.WIDTH_IN_TILES * LevelManager.TILE_WIDTH; x +=  LevelManager.TILE_WIDTH * (43/ Constants.PixelsPerMeter)){
            for (float y = 0; y < LevelManager.HEIGHT_IN_TILES* LevelManager.TILE_HEIGHT; y +=  LevelManager.TILE_HEIGHT * (43/ Constants.PixelsPerMeter)){
                if(!LevelManager.graph.getNodeByXYFloat(x,y).flow.isZero()) {
                    shapeRenderer.line(x + LevelManager.TILE_WIDTH/2,
                            y + LevelManager.TILE_HEIGHT/2,
                            x + LevelManager.TILE_WIDTH/2 + (LevelManager.graph.getNodeByXYFloat(x,y).flow.nor().x/2),
                            y + LevelManager.TILE_HEIGHT/2 + (LevelManager.graph.getNodeByXYFloat(x,y).flow.nor().y/2));
                }
            }
        }
        shapeRenderer.end();
    }
}
