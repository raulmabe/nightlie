package com.nameless.game.screens.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nameless.game.Constants;
import com.nameless.game.MainGame;

public class SkinsStage extends BasicMenuStage {

    private int indexSkin = 0;
    private Array<String> skins;

    private Image currentImage;
    private TextureAtlas atlas;
    private Table skinTable;

    private TextButton selectButton;


    public SkinsStage(final MainGame game, Viewport viewport, ChangeListener listener) {
        super(game, viewport, listener);
        atlas = new TextureAtlas("players/characters.atlas");

        // High Table
        skinTable = new Table();
        skinTable.setFillParent(true);
        skinTable.pad(Constants.STAGE_PADDING);
        addActor(skinTable);
//        skinTable.debug();

        skins = new Array<String>(Constants.Characters);
        skins.add("manBrown");
        skins.add("manBlue");
        skins.add("manRed");
        skins.add("womanGreen");
        skins.add("manOld");
        skins.add("womanOld");
        skins.add("survivor1");
        skins.add("survivor2");
        skins.add("soldier1");
        skins.add("soldier2");
        skins.add("robot1");
        skins.add("robot2");
        skins.add("hitman1");
        skins.add("hitman2");
        skins.add("zombie1");
        skins.add("zombie2");
        for(int i = 0; i < skins.size; ++i) if(Constants.character.equals(skins.get(i))) indexSkin = i;

        TextureRegion region = atlas.findRegion(skins.get(indexSkin) + "_reload");
        currentImage = new Image(region);
        currentImage.setOrigin(currentImage.getWidth() / 2, currentImage.getHeight() / 2);
        currentImage.rotateBy(90);
        skinTable.add(currentImage).size(currentImage.getWidth()*1.5f, currentImage.getHeight()*1.5f)
                .align(Align.center);
//        skinTable.debug();


        // Low table
        Table table = new Table();
//        table.debug();
        table.setFillParent(true);
        table.pad(Constants.STAGE_PADDING);
        addActor(table);

        final TextButton backButton;
        backButton = new TextButton("Back", game.getSkin(), "new_grey");
        backButton.addListener(listener);

        selectButton = new TextButton("Select", game.getSkin(), "new_grey");
        selectButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Constants.character = skins.get(indexSkin);
                selectButton.setDisabled(true);
                backButton.setChecked(true);
                backButton.setChecked(false);
            }
        });
        selectButton.setDisabled(true);
        table.add(selectButton).fillX().expandX().expandY().height(80).padTop(20).align(Align.bottom);
        table.row();
        table.add(backButton).fillX().expandX().height(80).padTop(5).align(Align.bottom);
    }

    public void changeImage(int amount) {
        indexSkin += amount;
        indexSkin = MathUtils.clamp(indexSkin, 0, Constants.Characters-1);

        TextureRegion region = atlas.findRegion(skins.get(indexSkin) + "_reload");
        currentImage = new Image(region);
        currentImage.setOrigin(currentImage.getWidth() / 2, currentImage.getHeight() / 2);
        currentImage.rotateBy(90);

        skinTable.clearChildren();
        skinTable.add(currentImage).size(currentImage.getWidth()*1.5f, currentImage.getHeight()*1.5f)
                .align(Align.center);

        if(Constants.character.equals(skins.get(indexSkin))) selectButton.setDisabled(true);
        else selectButton.setDisabled(false);
    }
}
