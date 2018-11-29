package com.nameless.game.flowfield;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.nameless.game.maps.LevelManager;
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

    public static void drawFlow(){}
}
