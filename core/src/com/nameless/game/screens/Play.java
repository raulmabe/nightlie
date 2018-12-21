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
import com.nameless.game.scene2d.ui.*;

import static com.nameless.game.Constants.PixelsPerMeter;

public class Play extends BasicPlay{

    private WaveSpawnManager waveSpawnManager;
    private DayNightCycleManager dayManager;

    public Play(MainGame game) {
        super(game);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void setUpInterface(Table table) {
        super.setUpInterface(table);
        waveSpawnManager = new WaveSpawnManager(this);
        dayManager = DayNightCycleManager.getInstance();
        waveSpawnManager.attach(dayManager);
        waveSpawnManager.attach(hud);
    }

    @Override
    public void pause() {
        super.pause();
        if(state != GAME_PAUSED){
            waveSpawnManager.pause();
        }
    }

    /**
     * It's called every frame
     * @param delta
     */
    @Override
    public void render(float delta) {
        super.render(delta);
        waveSpawnManager.update(delta);
    }


    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void resume() {
        super.resume();
        waveSpawnManager.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void clearScene() {
        waveSpawnManager.clearScene();
        super.clearScene();
    }


}
