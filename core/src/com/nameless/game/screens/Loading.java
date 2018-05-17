package com.nameless.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.nameless.game.MainGame;

/**
 * Created by Raul on 13/06/2017.
 */

public class Loading extends BasicScreen {

    public Loading(MainGame game) {
        super(game);
    }

    private ShapeRenderer shaper;

    @Override
    public void setUpInterface(Table table) {

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        float barWidth = width*0.8f;
        float barHeight = height*0.1f;
        float barX = (width-barWidth)/2;
        float barY = (height-barHeight)/2;


        if(game.manager.update(1000/120)){
            game.finishLoading();
        }
        else{
            float progress = game.manager.getProgress();

            shaper.setAutoShapeType(true);
            shaper.begin();
            shaper.set(ShapeRenderer.ShapeType.Line);
            shaper.rect(barX-5,barY-5,barWidth+10,barHeight+10);
            shaper.set(ShapeRenderer.ShapeType.Filled);
            shaper.rect(barX,barY,barWidth*progress,barHeight);
            shaper.end();
        }
    }

    @Override
    public void show() {
        super.show();
        shaper = new ShapeRenderer();
    }
}
