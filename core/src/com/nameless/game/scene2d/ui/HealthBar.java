package com.nameless.game.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.nameless.game.Constants;
import com.nameless.game.actors.Character;
import com.nameless.game.actors.player.Player;
import com.nameless.game.screens.Play;

public class HealthBar extends Actor {

    private ShapeRenderer sr;
    private Player character;

    public HealthBar(Player character){
        this.character = character;
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);

        setSize(character.getWidth(),character.getHeight()/8);
        setPosition(character.getX(), character.getY() + character.getHeight() *1.5f);
        setColor(new Color(255/255f, 80/255f, 80/255f, 1f));
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();
        sr.setProjectionMatrix(batch.getProjectionMatrix());
        sr.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.rect(getX(), getY(), getWidth(), getHeight());
        sr.end();

        batch.begin();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setPosition(character.getX(), character.getY() + character.getHeight() *1.5f);
        setWidth(character.getWidth()*character.getHEALTH()/character.MAX_HEALTH   );
//        setPosition(player.getStagePos().x*Constants.PixelsPerMeter, player.getStagePos().y*Constants.PixelsPerMeter);
    }
}
