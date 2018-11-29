package com.nameless.game.scene2d.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nameless.game.Constants;
import com.nameless.game.MainGame;
import com.nameless.game.VirtualController;
import com.nameless.game.Weapons;
import com.nameless.game.actors.player.Player;
import com.nameless.game.flowfield.FlowFieldManager;
import com.nameless.game.screens.Menu;
import com.nameless.game.screens.Play;

/**
 * Created by Raul on 17/06/2017.
 */

public class Hud extends Group implements InputProcessor {

    public Stage hud;
    private Viewport viewport;
    public OrthographicCamera camera;

    private ImageButton quit;

    private final Table table;

    private Touchpad touchpad, touchpad2;

    private MainGame game;
    private VirtualController controller;
    private Play playScreen;

    public Label timeToNextSpawn;
    public Label timeHour;

    public Hud(final MainGame game, final VirtualController controller, Play playScreen) {
        this.game = game;
        this.controller = controller;
        this.playScreen = playScreen;

        camera = new OrthographicCamera();
        viewport = new FitViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT, camera);
        hud = new Stage(viewport, game.getBatch());

        timeToNextSpawn = new Label("Time", game.getSkin());
        timeToNextSpawn.setPosition(hud.getViewport().getWorldWidth()/2 - timeToNextSpawn.getWidth()/2,hud.getViewport().getWorldHeight() - 60);
        hud.addActor(timeToNextSpawn);

        timeHour = new Label("12h", game.getSkin());
        timeHour.setPosition(hud.getViewport().getWorldWidth() - timeHour.getWidth()*2,hud.getViewport().getWorldHeight() - 60);
        hud.addActor(timeHour);

//        hud.setDebugAll(true);
        table = new Table();
        table.setFillParent(true);
        table.pad(20);

        quit = new ImageButton(game.getSkin(), "quit");
        quit.setSize(50, 50);
        quit.setPosition(60, hud.getViewport().getWorldHeight() - 60);
        quit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ConfirmDialog dialog = askQuit();
                table.add(dialog).expand().fillX().align(Align.center);
                hud.addActor(table);
            }
        });
        hud.addActor(quit);



        touchpad = new Touchpad(10, game.getSkin());
        touchpad.setBounds(35, 15, 300, 300);
        touchpad.setColor(touchpad.getColor().r, touchpad.getColor().g, touchpad.getColor().b, 1);
        hud.addActor(touchpad);

        Stock stock = new Stock(game);
        hud.addActor(stock);

        touchpad2 = new Touchpad(10, game.getSkin());
        touchpad2.setBounds(hud.getViewport().getWorldWidth() - 300 - 35, 15, 300, 300);
        touchpad2.setColor(touchpad2.getColor().r, touchpad2.getColor().g, touchpad2.getColor().b, 1);
        hud.addActor(touchpad2);
    }

    private ConfirmDialog askQuit() {
        final ConfirmDialog dialog = new ConfirmDialog(game.getSkin(), "Are you sure you want to quit?", "Yes", "No");
        playScreen.state = playScreen.GAME_PAUSED;
        dialog.setCallback(new ConfirmDialog.ConfirmCallback() {
            @Override
            public void ok() {
                //Sonido
                game.setScreen(new Menu(game));
            }

            @Override
            public void cancel() {
                //Sonido
                table.clearChildren();
                playScreen.state = playScreen.GAME_RUNNING;
            }
        });
        return dialog;
    }

    public void update(float dt){
        camera.update();
        hud.act(dt);
        hud.draw();

        if(touchpad.isTouched()) touchpad.setColor(touchpad.getColor().r, touchpad.getColor().g, touchpad.getColor().b, 1);
        else touchpad.setColor(touchpad.getColor().r, touchpad.getColor().g, touchpad.getColor().b, 0);

        if(touchpad2.isTouched()) touchpad2.setColor(touchpad2.getColor().r, touchpad2.getColor().g, touchpad2.getColor().b, 1);
        else touchpad2.setColor(touchpad2.getColor().r, touchpad2.getColor().g, touchpad2.getColor().b, 0);

        controller.MovePercentX = touchpad.getKnobPercentX();
        controller.MovePercentY = touchpad.getKnobPercentY();

        controller.TurnPercentX = touchpad2.getKnobPercentX();
        controller.TurnPercentY = touchpad2.getKnobPercentY();
    }

    public void dispose(){
        hud.dispose();
    }

    public void resize(int width, int height){
        hud.getViewport().update(width,height);
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.SPACE) controller.shoot = true;
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.SPACE) controller.shoot = false;
        if(keycode == Input.Keys.F) {
            if(controller.Light_on) controller.Light_on = false;
            else controller.Light_on = true;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {return false;}

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
