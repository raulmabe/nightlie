package com.nameless.game.maps;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.nameless.game.Constants;
import com.nameless.game.DayNightCycleManager;
import com.nameless.game.MainGame;
import com.nameless.game.box2d.CollisionManager;

import java.util.ArrayList;

/**
 * Created by Raul on 27/06/2017.
 */
public abstract class BasicMap {

    /*
    Must-have layers:
    - ActorPosition

    Render foreground layers:
        - Trees1
        - Trees2
     */


    TiledMap tiledmap;
    OrthogonalTiledMapRendererWithSprites tiledMapRenderer;


    public RayHandler rayHandler = null;
    private ArrayList<PointLight> lights;

    public World world;
    CollisionManager collisionManager;
    Box2DDebugRenderer worldRenderer;

    float unitScale = 1f;

    private MainGame game;

    BasicMap(MainGame game, String filename, float unitScale) {
        this.unitScale = unitScale;
        tiledmap = game.manager.get(filename);
        tiledMapRenderer = new OrthogonalTiledMapRendererWithSprites(tiledmap, unitScale);

        // Atributs
        LevelManager.WIDTH_IN_TILES = tiledmap.getProperties().get("map_size", int.class);
        LevelManager.HEIGHT_IN_TILES = tiledmap.getProperties().get("map_size", int.class);
        LevelManager.TILE_WIDTH = (float)tiledmap.getProperties().get("tile_size", int.class);
        LevelManager.TILE_HEIGHT = (float)tiledmap.getProperties().get("tile_size", int.class);
        LevelManager.WIDTH_IN_PIXELS = LevelManager.WIDTH_IN_TILES * LevelManager.TILE_WIDTH * unitScale;
        LevelManager.HEIGHT_IN_PIXELS = LevelManager.HEIGHT_IN_TILES * LevelManager.TILE_HEIGHT * unitScale;
        LevelManager.unitScale = unitScale;
        LevelManager.loadGraph(tiledmap);

        // Box2D
        world = new World(new Vector2(0,0), true);
        collisionManager = new CollisionManager();
        world.setContactListener(collisionManager);
        worldRenderer = new Box2DDebugRenderer();

        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(DayNightCycleManager.dayTime);
        lights = new ArrayList<PointLight>();
    }

    public void render (OrthographicCamera camera){
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        for(PointLight light : lights) light.setActive(DayNightCycleManager.lightsOpen);
        rayHandler.setAmbientLight(DayNightCycleManager.dayTime);


        // Debug
       //worldRenderer.render(world, camera.combined);
    }

    public void render(OrthographicCamera camera, int x, int y, int w, int h){
        tiledMapRenderer.setView(camera.combined, x, y, w, h);
        tiledMapRenderer.render();

        for(PointLight light : lights) light.setActive(DayNightCycleManager.lightsOpen);
        rayHandler.setAmbientLight(DayNightCycleManager.dayTime);


        // Debug
        //worldRenderer.render(world, camera.combined);
    }

    public void renderWalls(OrthographicCamera camera, int x, int y, int w, int h){
        tiledMapRenderer.setView(camera.combined, x, y, w, h);
        tiledMapRenderer.getBatch().begin();
        tiledMapRenderer.renderTileLayer((TiledMapTileLayer) tiledmap.getLayers().get("Walls"));
        tiledMapRenderer.getBatch().end();
    }

    public void renderTreesLayers(OrthographicCamera camera, int x, int y, int w, int h){
        tiledMapRenderer.setView(camera.combined, x, y, w, h);
        tiledMapRenderer.getBatch().begin();

        tiledMapRenderer.renderLightsObject(tiledmap.getLayers().get("Trees1"), DayNightCycleManager.dayTime);
        tiledMapRenderer.renderLightsObject(tiledmap.getLayers().get("Trees2"), DayNightCycleManager.dayTime);

        tiledMapRenderer.getBatch().end();
    }

    public void renderTreesLayers(OrthographicCamera camera){
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.getBatch().begin();

        tiledMapRenderer.renderLightsObject(tiledmap.getLayers().get("Trees1"), DayNightCycleManager.dayTime);
        tiledMapRenderer.renderLightsObject(tiledmap.getLayers().get("Trees2"), DayNightCycleManager.dayTime);

        tiledMapRenderer.getBatch().end();
    }

    public void dispose (){
        for (PointLight light: lights) {
            light.dispose();
        }
        tiledmap.dispose();
        tiledMapRenderer.dispose();
        world.dispose();
        worldRenderer.dispose();
    }

    public Vector2 getPositionPlayer(){
        Vector2 vec = new Vector2(0,0);
        for(MapObject object : tiledmap.getLayers().get("ActorPosition").getObjects().getByType(EllipseMapObject.class)){
            if(object.getProperties().containsKey("player")) {
                vec.x = ((EllipseMapObject) object).getEllipse().x
                       + MathUtils.random(((EllipseMapObject) object).getEllipse().width);
                vec.y = ((EllipseMapObject) object).getEllipse().y
                       + MathUtils.random(((EllipseMapObject) object).getEllipse().height);
            }
        }
        return vec;
    }

    public int getWidth() {
        return ((TiledMapTileLayer) tiledmap.getLayers().get(0)).getWidth();
    }

    public int getHeight() {
        return ((TiledMapTileLayer) tiledmap.getLayers().get(0)).getHeight();
    }

    public int getCountLayers(){
        return tiledmap.getLayers().getCount();
    }

    protected void createRoundLight(Body body, float x, float y){
        PointLight light = new PointLight(rayHandler, 20, new Color(1f,1f,0f,.65f), 10, x,y);
        light.attachToBody(body);
        light.setSoftnessLength(0f);
        light.setActive(true);
        light.setContactFilter(Constants.LOW_FURNITURES_BIT, (short) 0x0000, (short) (Constants.OBSTACLES_BIT | Constants.ENEMY_BIT | Constants.PLAYER_BIT));
        lights.add(light);
    }

    public void renderRayHandler(OrthographicCamera cam) {
        rayHandler.updateAndRender();
        rayHandler.setCombinedMatrix(cam);
    }
}