package com.nameless.game.screens.stages;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nameless.game.Constants;
import com.nameless.game.MainGame;

public class SettingsStage extends BasicMenuStage {
    public SettingsStage(final MainGame game, Viewport viewport, ChangeListener listener, ChangeListener NightListener) {
        super(game, viewport, listener);
        Table table = new Table();
        table.setFillParent(true);
        table.pad(Constants.STAGE_PADDING);
        addActor(table);

        Table miniTable = new Table();
        table.pad(Constants.STAGE_PADDING);

        final CheckBox checkbox = new CheckBox("Night", game.getSkin());
        if(game.prefs.getBoolean("night")) checkbox.setChecked(true);
        checkbox.addCaptureListener(NightListener);
        miniTable.add(checkbox).align(Align.top);

        table.add(miniTable).expandX().expandY().padTop(20).align(Align.top).row();

        TextButton backButton;
        backButton = new TextButton("Back", game.getSkin(), "new_grey");
        backButton.addListener(listener);
        table.add(backButton).fillX().expandX().expandY().height(80).padTop(20).align(Align.bottom);

    }
}
