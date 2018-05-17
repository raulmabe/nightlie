package com.nameless.game.screens.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nameless.game.Constants;
import com.nameless.game.MainGame;

public class InfoStage extends BasicMenuStage{
    public InfoStage(MainGame game, Viewport viewport, ChangeListener listener) {
        super(game, viewport, listener);
        Table infoTable, table;
        ScrollPane scroll;
        Label label1, labelname, label2, labelhost;
        TextButton backButton;

        table = new Table();
        table.setFillParent(true);
        table.pad(Constants.STAGE_PADDING);
        addActor(table);


        infoTable = new Table();
        label1 = new Label("Name: ", game.getSkin(), "bold");
        labelname = new Label(Constants.NAME, game.getSkin());
        label2 = new Label("Localhost: " , game.getSkin(), "bold");
        labelhost = new Label(Constants.IP, game.getSkin());
        infoTable.add(label1).align(Align.left);
        infoTable.add(labelname).align(Align.right);
        infoTable.row();
        infoTable.add(label2).align(Align.left);
        infoTable.add(labelhost).align(Align.right);
        infoTable.debug();


        scroll = new ScrollPane(infoTable, game.getSkin());
        scroll.setFadeScrollBars(false);


        backButton = new TextButton("Back", game.getSkin(), "new_grey");
        backButton.addListener(listener);
        table.add(scroll).colspan(2).align(Align.topLeft).expand().fill().row();
        table.add(backButton).fillX().expandX().expandY().height(80).padTop(20).align(Align.bottom);

    }
}
