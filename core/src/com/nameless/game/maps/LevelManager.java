package com.nameless.game.maps;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.nameless.game.pathfinding.GraphGenerator;
import com.nameless.game.pathfinding.GraphImp;

public class LevelManager {

    public static float WIDTH_IN_PIXELS;
    public static float HEIGHT_IN_PIXELS;
    public static int WIDTH_IN_TILES;
    public static int HEIGHT_IN_TILES;
    public static float TILE_WIDTH;
    public static float TILE_HEIGHT;
    public static float unitScale;

    public static GraphImp graph;

    public static void loadGraph(TiledMap tiledMap){
        TILE_WIDTH = TILE_WIDTH * unitScale;
        TILE_HEIGHT = TILE_HEIGHT * unitScale;
        graph = GraphGenerator.generateGraph(tiledMap);
    }
}
