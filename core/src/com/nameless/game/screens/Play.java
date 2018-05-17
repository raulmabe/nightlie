package com.nameless.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.nameless.game.Constants;
import com.nameless.game.DayNightCycleManager;
import com.nameless.game.MainGame;
import com.nameless.game.VirtualController;
import com.nameless.game.actors.enemies.Zombie;
import com.nameless.game.actors.player.Player;
import com.nameless.game.maps.BasicMap;
import com.nameless.game.maps.TownMap;
import com.nameless.game.pathfinding.PathfindingDebugger;
import com.nameless.game.scene2d.ui.Hud;

import java.util.ArrayList;

import static com.nameless.game.Constants.PixelsPerMeter;

public class Play extends BasicScreen{
    public final int GAME_RUNNING = 0;
    public final int GAME_PAUSED = 1;
    public final int GAME_WAITING = 2;
    public int state = 0;

    private BasicMap map;
    public Player player;

    public ArrayList<Zombie> enemies;

    FPSLogger fps;

    public VirtualController controller;

    public Group mapHud, bg,fg;
    public Hud hud;

    private InputMultiplexer inputMulti;

    private float timeToNextSpawn = TimeUtils.nanoTime();
    private int round;

    public Play(MainGame game) {
        super(game);
        inputMulti = new InputMultiplexer();
        mapHud = new Group();
        mapHud.setScale(1/PixelsPerMeter);
        bg = new Group();
        fg = new Group();

        enemies = new ArrayList<Zombie>();

        map = new TownMap(game, 1/PixelsPerMeter);

        Vector2 PlayerPos = map.getPositionPlayer();

        controller = new VirtualController();

        // Spawn Player
        if(!game.prefs.getBoolean("night")) player = new Player(this,null, map.world, PlayerPos.x, PlayerPos.y);
        else player = new Player(this,map.rayHandler, map.world, PlayerPos.x, PlayerPos.y);

        PathfindingDebugger.setCamera(cam);

        hud = new Hud(game, controller, this);

        round = 1;
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        hud.resize(width, height);
    }

    @Override
    public void setUpInterface(Table table) {
        fg.addActor(player);
        stage.addActor(bg);
        stage.addActor(fg);

        inputMulti.addProcessor(hud.hud);
        inputMulti.addProcessor(hud);
        Gdx.input.setInputProcessor(inputMulti);

        viewport.setWorldSize(viewport.getWorldWidth()/PixelsPerMeter, viewport.getWorldHeight()/PixelsPerMeter);

        fps = new FPSLogger();
//        stage.setDebugAll(true);
//
//        float delay = 1f;
//
//        Timer.schedule(new Timer.Task(){
//            @Override
//            public void run() {
//                player.TakeDamage(1000,new Vector2(0,0));
//            }
//        }, delay);


    }

    /**
     * Reorganize by switch game states
     * @param delta
     */

    @Override
    public void render(float delta) {
        super.render(delta);
//        handleInput();
        cam.position.x = player.getX() + player.getWidth()/2;
        cam.position.y = player.getY() + player.getHeight()/2;
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) cam.zoom -= 0.1f;
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) cam.zoom += 0.1f;
        cam.update();

        // Map
        if(state == GAME_RUNNING || state == GAME_WAITING) map.world.step(1/60f, 6, 2);
        map.render(cam);

        // Stage
        if(state == GAME_RUNNING || state == GAME_WAITING) stage.act(delta);
        stage.draw();

        // Render RayCast Light
        map.renderRayHandler(cam);

        // Render fore layers
        map.renderForeLayers();

        mapHud.act(delta);
        stage.getBatch().begin();
        mapHud.draw(stage.getBatch(), 1);
        stage.getBatch().end();

        // Hud
        hud.update(delta);

        // Spawn control
        if(enemies.size() == 0 && state != GAME_WAITING){
            state = GAME_WAITING;
            timeToNextSpawn = TimeUtils.nanoTime();
        }
        if(state == GAME_WAITING){
            if(TimeUtils.nanoTime() - timeToNextSpawn > 2000000000){
                round++;
                WaveSpawn(round);
            } else{
//                hud.timeToNextSpawn.setText(""+ (2 - (MathUtils.nanoToSec * (TimeUtils.nanoTime() - timeToNextSpawn))));
//                hud.timeToNextSpawn.setText("" + round);
            }
        }


        // Debug
//         fps.log();
        hud.timeToNextSpawn.setText("" + Gdx.graphics.getFramesPerSecond());
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        super.dispose();
        hud.dispose();
    }

    public void clearScene() {
        for (int i = 0; i < enemies.size(); ++i) {
            enemies.get(i).remove();
        }
        enemies.clear();
        game.setScreen(new Menu(game));
    }

    private void WaveSpawn(int round){
        float delay = .1f; // seconds
        final Play instance = this;
//
        for(int i = 0;i < 10; ++i){  // i < (round*2 + 10)
            Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    Vector2 pos = map.getPositionEnemy();
                    Zombie zombie = new Zombie(instance, map.world, player, pos.x, pos.y);
                    enemies.add(zombie);
                    fg.addActor(zombie);
                }
            }, delay * i);
        }
        state = GAME_RUNNING;
    }
}
