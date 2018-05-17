package com.nameless.game.scene2d.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.nameless.game.Constants;
import com.nameless.game.Weapons;
import com.nameless.game.actors.player.Player;


public class WeaponInfo extends Label
{
    private Player player;

    public WeaponInfo(Player player){
        super("NONE", player.play.game.getSkin(), "small");
        this.player = player;

        switch (player.play.controller.ACTUAL_WEAPON){
            case Weapons.ROCKET:
                setText(Weapons.ROCKET_STRING + " " + player.weapons[Weapons.ROCKET]);
                break;
            case Weapons.GRENADE:
                setText(Weapons.GRENADE_STRING + " " + player.weapons[Weapons.GRENADE]);
                break;
            case Weapons.SHOTGUN:
                setText(Weapons.SHOTGUN_STRING + " " + player.weapons[Weapons.SHOTGUN]);
                break;
            case Weapons.UZI:
                setText(Weapons.UZI_STRING + " " + player.weapons[Weapons.UZI]);
                break;
            case Weapons.PISTOL:
                setText(Weapons.PISTOL_STRING + " " + player.weapons[Weapons.PISTOL]);
                break;
            case Weapons.NONE:
            default:
                setText("NONE");
        }

        setSize(player.getWidth(), player.getHeight());
        setPosition((player.getX()+player.getWidth()/2)*Constants.PixelsPerMeter, (player.getY() + player.getHeight() *2f)*Constants.PixelsPerMeter);
        setFontScale(.5f);
        setColor(Color.WHITE);
        setWrap(true);
        setAlignment(Align.center);
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setPosition((player.getX()+player.getWidth()/2)*Constants.PixelsPerMeter, (player.getY() + player.getHeight() *2f)*Constants.PixelsPerMeter);

        switch (player.play.controller.ACTUAL_WEAPON){
            case Weapons.ROCKET:
                setText(Weapons.ROCKET_STRING + " " + player.weapons[Weapons.ROCKET]);
                break;
            case Weapons.GRENADE:
                setText(Weapons.GRENADE_STRING + " " + player.weapons[Weapons.GRENADE]);
                break;
            case Weapons.SHOTGUN:
                setText(Weapons.SHOTGUN_STRING + " " + player.weapons[Weapons.SHOTGUN]);
                break;
            case Weapons.UZI:
                setText(Weapons.UZI_STRING + " " + player.weapons[Weapons.UZI]);
                break;
            case Weapons.PISTOL:
                setText(Weapons.PISTOL_STRING + " " + player.weapons[Weapons.PISTOL]);
                break;
            case Weapons.NONE:
            default:
                setText("NONE");
        }
        setAlignment(Align.center);
    }
}
