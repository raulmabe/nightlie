package com.nameless.game.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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

    private ShapeRenderer shaper;

    private int SIZE_ICONS = 75;

    public Stock(MainGame game) {
        shaper = new ShapeRenderer();

        buildButtons(game);

        setFillParent(true);
        pad(Constants.STAGE_PADDING);
        align(Align.bottom);
        add(pistolButton).size(SIZE_ICONS);
        add(uziButton).size(SIZE_ICONS);
        add(shotgunButton).size(SIZE_ICONS);
        add(grenadeButton).size(SIZE_ICONS);
        add(rocketButton).size(SIZE_ICONS);
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
        batch.end();

        shaper.setProjectionMatrix(batch.getProjectionMatrix());

        shaper.begin(ShapeRenderer.ShapeType.Line);
        shaper.setColor(Color.WHITE);

        switch (VirtualController.ACTUAL_WEAPON){
            case Weapons.ROCKET:
                shaper.box(rocketButton.getX(), rocketButton.getY(), 0, SIZE_ICONS,SIZE_ICONS,0);
                break;
            case Weapons.GRENADE:
                shaper.box(grenadeButton.getX(), grenadeButton.getY(), 0, SIZE_ICONS,SIZE_ICONS,0);
                break;
            case Weapons.SHOTGUN:
                shaper.box(shotgunButton.getX(), shotgunButton.getY(), 0, SIZE_ICONS,SIZE_ICONS,0);
                break;
            case Weapons.UZI:
                shaper.box(uziButton.getX(), uziButton.getY(), 0, SIZE_ICONS,SIZE_ICONS,0);
                break;
            case Weapons.PISTOL:
                shaper.box(pistolButton.getX(), pistolButton.getY(), 0, SIZE_ICONS,SIZE_ICONS,0);
                break;
            default:
        }
        shaper.end();

        batch.begin();
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
