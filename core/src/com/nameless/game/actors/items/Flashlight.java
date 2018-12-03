package com.nameless.game.actors.items;

import box2dLight.ConeLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;
import com.nameless.game.Constants;

import static com.nameless.game.Constants.PixelsPerMeter;

public class Flashlight {

    ConeLight coneLight;

    public Flashlight(RayHandler rayHandler, float rotation, float offSetY, Body body) {
        coneLight = new ConeLight(rayHandler, 100, Color.BLACK, 20, 2, 2, rotation+90, 30);
        coneLight.attachToBody(body, 0, offSetY/PixelsPerMeter, rotation);
        coneLight.setSoftnessLength(0f);
        coneLight.setContactFilter(Constants.LOW_FURNITURES_BIT, (short) 0x0000, (short) (Constants.OBSTACLES_BIT | Constants.NEUTRAL_BIT | Constants.ENEMY_BIT | Constants.PLAYER_BIT));
        coneLight.setActive(true);
    }

    public void update(boolean on){
        if(on) coneLight.setActive(true);
        else coneLight.setActive(false);
    }
}
