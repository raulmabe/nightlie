package com.nameless.game.screens.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nameless.game.Constants;
import com.nameless.game.MainGame;

public class StatisticsStage extends BasicMenuStage {
    public StatisticsStage(MainGame game, Viewport viewport, ChangeListener listener) {
        super(game, viewport, listener);
        Table table = new Table();
        table.setFillParent(true);
        table.pad(Constants.STAGE_PADDING);
        addActor(table);

        TextButton backButton;
        backButton = new TextButton("Back", game.getSkin(), "new_grey");
        backButton.addListener(listener);
        table.add(backButton).fillX().expandX().expandY().height(80).padTop(20).align(Align.bottom);
    }
}
