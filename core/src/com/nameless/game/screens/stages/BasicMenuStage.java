package com.nameless.game.screens.stages;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nameless.game.MainGame;


public abstract class BasicMenuStage extends Stage{

    private MainGame game;
    private ChangeListener listener;

    public BasicMenuStage(MainGame game, Viewport viewport, ChangeListener listener) {
        super(viewport, game.getBatch());
        this.game = game;
        this.listener = listener;
    }
}
