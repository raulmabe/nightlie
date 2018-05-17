package com.nameless.game.scene2d.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.nameless.game.Constants;
import com.nameless.game.MainGame;
import com.nameless.game.VirtualController;
import com.nameless.game.Weapons;
public class Stock extends Table {

    private ImageButton pistolButton, uziButton, shotgunButton, grenadeButton, rocketButton;

    public Stock(MainGame game) {

        buildButtons(game);

        setFillParent(true);
        pad(Constants.STAGE_PADDING);
        align(Align.bottom);
        add(pistolButton).size(75,75);
        add(uziButton).size(75,75);
        add(shotgunButton).size(75,75);
        add(grenadeButton).size(75,75);
        add(rocketButton).size(75,75);
        EnableWeapon(VirtualController.ACTUAL_WEAPON);
    }

    private void buildButtons(MainGame game) {
        pistolButton = new ImageButton(game.getSkin(), "pistol");
        uziButton = new ImageButton(game.getSkin(), "uzi");
        shotgunButton = new ImageButton(game.getSkin(), "shotgun");
        grenadeButton = new ImageButton(game.getSkin(), "grenade");
        rocketButton = new ImageButton(game.getSkin(), "rocket");

        pistolButton.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                EnableWeapon(Weapons.PISTOL);
            }
        });
        uziButton.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                EnableWeapon(Weapons.UZI);
            }
        });
        shotgunButton.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                EnableWeapon(Weapons.SHOTGUN);
            }
        });
        grenadeButton.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                EnableWeapon(Weapons.GRENADE);
            }
        });
        rocketButton.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                EnableWeapon(Weapons.ROCKET);
            }
        });
    }

    private void EnableWeapon(int weapon) {
        VirtualController.ACTUAL_WEAPON = weapon;
        rocketButton.setColor(getColor().r, getColor().g, getColor().b, 0.5f);
        grenadeButton.setColor(getColor().r, getColor().g, getColor().b, 0.5f);
        shotgunButton.setColor(getColor().r, getColor().g, getColor().b, 0.5f);
        uziButton.setColor(getColor().r, getColor().g, getColor().b, 0.5f);
        pistolButton.setColor(getColor().r, getColor().g, getColor().b, 0.5f);

        {
            switch (weapon){
                case Weapons.ROCKET:
                    rocketButton.getColor().a = 1;
                    break;
                case Weapons.GRENADE:
                    grenadeButton.getColor().a = 1;
                    break;
                case Weapons.SHOTGUN:
                    shotgunButton.getColor().a = 1;
                    break;
                case Weapons.UZI:
                    uziButton.getColor().a = 1;
                    break;
                case Weapons.PISTOL:
                    pistolButton.getColor().a = 1;
                    break;
                default:
            }
        }


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void UpdateWeapon(int i){
        Weapons.LevelUp(i);
        switch (i){
            case Weapons.ROCKET:
                break;
            case Weapons.GRENADE:
                break;
            case Weapons.SHOTGUN:
                break;
            case Weapons.UZI:
                break;
            case Weapons.PISTOL:
                break;
            default:
        }
    }
}
