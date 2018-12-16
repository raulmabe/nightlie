package com.nameless.game.scene2d.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nameless.game.*;
import com.nameless.game.actors.player.Player;
import com.nameless.game.flowfield.FlowFieldManager;
import com.nameless.game.screens.Menu;
import com.nameless.game.screens.Play;

/**
 * Created by Raul on 17/06/2017.
 */

public class Hud extends Group implements IObserver {

    public Stage hud;
    private Viewport viewport;
    public OrthographicCamera camera;

    private ImageButton quit;

    private final Table table;

    private MainGame game;
    protected VirtualController controller;
    protected Play playScreen;

    public Label timeToNextSpawn;
    public Label timeHour;

    private Label roundStart;

    public Hud(final MainGame game, Play playScreen) {
        this.game = game;
        this.playScreen = playScreen;

        controller = VirtualController.getInstance();

        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        ((ScreenViewport) viewport).setUnitsPerPixel(0.5f);
        hud = new Stage(viewport, game.getBatch());

        roundStart = new Label("null", game.getSkin());
        roundStart.setPosition(hud.getViewport().getWorldWidth()/2 - roundStart.getWidth()/2,
                hud.getViewport().getWorldHeight()/1.5f - roundStart.getHeight()/2);
        roundStart.setFontScale(1.5f);
        roundStart.setAlignment(Align.center);

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

        Stock stock = new Stock(game);
        hud.addActor(stock);
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

    public void pause(){
        ConfirmDialog dialog = askQuit();
        table.add(dialog).expand().fillX().align(Align.center);
        hud.addActor(table);
    }

    public void update(float dt){
        camera.update();
        hud.act(dt);
        hud.draw();
    }

    public void dispose(){
        hud.dispose();
    }

    public void resize(int width, int height){
        hud.getViewport().update(width,height);
    }

    @Override
    public void handleMessage(Object o, ISubject.type type) {
        if(type == ISubject.type.ROUND_START){
            hud.addActor(roundStart);

            roundStart.setText("Night " + o);

            roundStart.setPosition(hud.getViewport().getWorldWidth()/2 - roundStart.getWidth()/2,
                    hud.getViewport().getWorldHeight());
            roundStart.addAction(Actions.moveTo(hud.getViewport().getWorldWidth()/2 - roundStart.getWidth()/2,
                    hud.getViewport().getWorldHeight()/1.5f - roundStart.getHeight()/2, .5f));

            Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    roundStart.remove();
                }
            }, 3.5f);
        }
    }
}
