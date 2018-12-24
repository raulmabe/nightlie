package com.nameless.game.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.nameless.game.Constants;
import com.nameless.game.VirtualController;
import com.nameless.game.WeaponsInfo;
import com.nameless.game.actors.player.Player;

public class Info extends Label {

    private float timer = 1.5f;
    private Player player;

    public Info(Player player, float x, float y, String s){
        super(s, player.play.game.getSkin(), "small");
        this.player = player;
        setSize(player.getWidth(), player.getHeight());
        setPosition(x* Constants.PixelsPerMeter, (y + player.getHeight() *2f)*Constants.PixelsPerMeter);
        setFontScale(.5f);
        setColor(Color.WHITE);
        setWrap(true);
        setAlignment(Align.center);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        timer -= delta;
        if(timer < 0) player.play.mapHud.removeActor(this);
    }
}
