package com.nameless.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nameless.game.Constants;
import com.nameless.game.MainGame;

/**
 * Created by Raul on 13/06/2017.
 */

public abstract class BasicScreen implements Screen{

    public final MainGame game;



    /**
     * Common stage.
     */
    public Stage stage = null;

    /**
     * Common Cam.
     */
    public static OrthographicCamera cam = null;

    /**
     * Common Viewport.
     */
    protected Viewport viewport = null;

    /**
     * Common table.
     */
    protected Table table = null;

    protected Skin skin = null;

    protected BasicScreen(MainGame game) {
        this.game = game;
    }

    @Override
    public void resize(int width, int height) {
//        cam.setToOrtho(false, width, height);
        stage.getViewport().update(width, height);
    }

    public void load() {

    }

    /**
     * This method sets up the visual layout for this screen. Child classes
     * have to override this method and add to the provided table the widgets
     * they want to show in the screen.
     *
     * @param table table that has been assigned to this screen.
     */
    public abstract void setUpInterface(Table table);

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();
        game.getBatch().setProjectionMatrix(cam.combined);

        //stage.act(delta);
        //stage.draw();
    }


    @Override
    public void show() {
        if (cam == null) {
            cam = new OrthographicCamera();
        }

        if (viewport == null) {
            //viewport = new FitViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT, cam);
            //viewport = new FitViewport(MainGame.width, MainGame.height, cam);
            viewport = new FitViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT, cam);
//            viewport = new ScreenViewport(cam);
        }


        if (stage == null) {
            stage = new Stage(viewport, game.getBatch());
        }


        if (table == null) {
            table = new Table();
            table.setFillParent(true);
            table.pad(Constants.STAGE_PADDING);
            stage.addActor(table);
        } else {
            table.clear();
        }
        /*if(skin == null){
            TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("skin/skin.atlas"));
            skin = new Skin(Gdx.files.internal("skin/skin.json"), atlas );
        }*/
        setUpInterface(table);

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
        stage = null;
        table = null;
    }

    Stage getStage() {
        return stage;
    }
}
