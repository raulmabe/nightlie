package com.nameless.game.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.nameless.game.Constants;
import com.nameless.game.VirtualController;
import com.nameless.game.WeaponsInfo;
import com.nameless.game.actors.player.Player;


public class WeaponsLabel extends Label
{
    private Player player;

    public WeaponsLabel(Player player){
        super("NONE", player.play.game.getSkin(), "small");
        this.player = player;

        switch (player.play.controller.ACTUAL_WEAPON){
            case WeaponsInfo.ROCKET:
                setText(WeaponsInfo.ROCKET_STRING + " " + player.weapons.getAmmo(WeaponsInfo.ROCKET));
                break;
            case WeaponsInfo.GRENADE:
                setText(WeaponsInfo.GRENADE_STRING + " " + player.weapons.getAmmo(WeaponsInfo.GRENADE));
                break;
            case WeaponsInfo.SHOTGUN:
                setText(WeaponsInfo.SHOTGUN_STRING + " " + player.weapons.getAmmo(WeaponsInfo.SHOTGUN));
                break;
            case WeaponsInfo.UZI:
                setText(WeaponsInfo.UZI_STRING + " " + player.weapons.getAmmo(WeaponsInfo.UZI));
                break;
            case WeaponsInfo.PISTOL:
                setText(WeaponsInfo.PISTOL_STRING + " " + player.weapons.getAmmo(WeaponsInfo.PISTOL));
                break;
            case WeaponsInfo.NONE:
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

        switch (VirtualController.ACTUAL_WEAPON){
            case WeaponsInfo.ROCKET:
                setText(WeaponsInfo.ROCKET_STRING + " " + player.weapons.getAmmo(WeaponsInfo.ROCKET));
                break;
            case WeaponsInfo.GRENADE:
                setText(WeaponsInfo.GRENADE_STRING + " " + player.weapons.getAmmo(WeaponsInfo.GRENADE));
                break;
            case WeaponsInfo.SHOTGUN:
                setText(WeaponsInfo.SHOTGUN_STRING + " " + player.weapons.getAmmo(WeaponsInfo.SHOTGUN));
                break;
            case WeaponsInfo.UZI:
                setText(WeaponsInfo.UZI_STRING + " " + player.weapons.getAmmo(WeaponsInfo.UZI));
                break;
            case WeaponsInfo.PISTOL:
                setText(WeaponsInfo.PISTOL_STRING + " " + player.weapons.getAmmo(WeaponsInfo.PISTOL));
                break;
            case WeaponsInfo.NONE:
            default:
                setText("NONE");
        }
        setAlignment(Align.center);
    }
}
