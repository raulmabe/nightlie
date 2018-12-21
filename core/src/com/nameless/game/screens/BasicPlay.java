package com.nameless.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.nameless.game.Constants;
import com.nameless.game.DayNightCycleManager;
import com.nameless.game.MainGame;
import com.nameless.game.VirtualController;
import com.nameless.game.actors.player.Player;
import com.nameless.game.flowfield.FlowFieldDebugger;
import com.nameless.game.flowfield.FlowFieldManager;
import com.nameless.game.managers.WaveSpawnManager;
import com.nameless.game.maps.BasicMap;
import com.nameless.game.maps.TownMap;
import com.nameless.game.pathfinding.PathfindingDebugger;
import com.nameless.game.scene2d.ui.Hud;
import com.nameless.game.scene2d.ui.HudMobileInput;
import com.nameless.game.scene2d.ui.HudPCInput;

import static com.nameless.game.Constants.PixelsPerMeter;

public class BasicPlay extends BasicScreen{
    public final int GAME_RUNNING = 0;
    public final int GAME_PAUSED = 1;
    public final int GAME_WAITING = 2;
    public int state = 0;

    public BasicMap map;
    public Player player;
    public VirtualController controller;

    public Group mapHud, bg,fg;
    public Hud hud;

    private InputMultiplexer inputMulti;

    public BasicPlay(MainGame game) {
        super(game);
        inputMulti = new InputMultiplexer();
        mapHud = new Group();
        mapHud.setScale(1/PixelsPerMeter);

        bg = new Group();
        fg = new Group();

        map = new TownMap(game, 1/PixelsPerMeter);

        Vector2 PlayerPos = map.getPositionPlayer();

        controller = VirtualController.getInstance();

        player = new Player(this, map.rayHandler, map.world, PlayerPos.x, PlayerPos.y);
        FlowFieldManager.calcDistanceForEveryNode(player.getCenterX(), player.getCenterY());

        PathfindingDebugger.setCamera(cam);
        FlowFieldDebugger.setCamera(cam);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        hud.resize(width, height);
    }

    @Override
    public void setUpInterface(Table table) {
        switch(Gdx.app.getType()) {
            case Android:
            case iOS:
                hud = new HudMobileInput(game, this);
                break;
            case Desktop:
            case WebGL:
            case HeadlessDesktop:
            default:
                hud = new HudPCInput(game, this);
        }

        /*
        waveSpawnManager = new WaveSpawnManager(this);
        dayManager = DayNightCycleManager.getInstance();
        waveSpawnManager.attach(dayManager);
        waveSpawnManager.attach(hud);*/

        ((ScreenViewport) viewport).setUnitsPerPixel(1/(PixelsPerMeter*2));

        fg.addActor(player);
        stage.addActor(bg);
        stage.addActor(fg);

        inputMulti.addProcessor(hud.hud);
        if(hud instanceof HudPCInput) inputMulti.addProcessor((HudPCInput) hud);
        Gdx.input.setInputProcessor(inputMulti);

        viewport.setWorldSize(viewport.getWorldWidth()/PixelsPerMeter, viewport.getWorldHeight()/PixelsPerMeter);
    }

    @Override
    public void pause() {
        super.pause();
        if(state != GAME_PAUSED){
            hud.pause();
        }
    }

    private void handleCamera(){
        cam.position.x = player.getCenterX();
        cam.position.y = player.getCenterY();

        if(Gdx.input.isKeyPressed(Input.Keys.UP)) cam.zoom -= 0.1f;
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) cam.zoom += 0.1f;
        cam.update();
    }

    /**
     * It's called every frame
     * @param delta
     */
    @Override
    public void render(float delta) {
        super.render(delta);
        handleCamera();
        //waveSpawnManager.update(delta);

        // Map
        if(state == GAME_RUNNING || state == GAME_WAITING) map.world.step(1/60f, 6, 2);
        map.render(cam, (int) (player.getCenterX() - Constants.RENDER_WIDTH/2), (int) (player.getCenterY() - Constants.RENDER_WIDTH/2) ,
                Constants.RENDER_WIDTH, Constants.RENDER_WIDTH);
        //FlowFieldDebugger.drawFlow();

        // Stage
        if(state == GAME_RUNNING || state == GAME_WAITING) stage.act(delta);
        stage.draw();

        map.renderWalls(cam, (int) (player.getCenterX() - Constants.RENDER_WIDTH/2), (int) (player.getCenterY() - Constants.RENDER_WIDTH/2) ,
                Constants.RENDER_WIDTH, Constants.RENDER_WIDTH);

        // Render RayCast Light
        map.renderRayHandler(cam);

        viewport.apply();

        // Render fore layers
        map.renderTreesLayers(cam, (int) (player.getCenterX() - Constants.RENDER_WIDTH/2), (int) (player.getCenterY() - Constants.RENDER_WIDTH/2) ,
                Constants.RENDER_WIDTH, Constants.RENDER_WIDTH);

        mapHud.act(delta);
        stage.getBatch().begin();
        mapHud.draw(stage.getBatch(), 1);
        stage.getBatch().end();

        hud.update(delta);
        hud.timeToNextSpawn.setText("" + Gdx.graphics.getFramesPerSecond());
    }


    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void resume() {
        super.resume();
        //waveSpawnManager.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
        hud.dispose();
    }

    public void clearScene() {
        //waveSpawnManager.clearScene();
        game.setScreen(new Menu(game));
    }


}
