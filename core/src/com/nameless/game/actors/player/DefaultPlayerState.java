package com.nameless.game.actors.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.nameless.game.*;
import com.nameless.game.actors.Character;
import com.nameless.game.actors.items.*;
import com.nameless.game.actors.states.IState;
import com.nameless.game.flowfield.FlowFieldManager;
import com.nameless.game.maps.LevelManager;
import com.nameless.game.pathfinding.PathfindingDebugger;

public class DefaultPlayerState implements IState {

    private Player parent;

    Vector2 touchpad, touchpad2, aux;

    //private int actualNode = 0;

    @Override
    public void Enter(Character parent) {
        this.parent = (Player) parent;

        //actualNode = LevelManager.graph.getIndexByXY(parent.getCenterX(), parent.getCenterY());

        touchpad = new Vector2(0,0);
        touchpad2 = new Vector2(0,0);
        aux = new Vector2(0,0);
    }

    @Override
    public void Update(float dt) {
        /*if(LevelManager.graph.getIndexByXY(parent.getCenterX(), parent.getCenterY()) != actualNode) {
            FlowFieldManager.calcDistanceForEveryNode(parent.getCenterX(),
                    parent.getCenterY());
            actualNode = LevelManager.graph.getIndexByXY(parent.getCenterX(), parent.getCenterY());
        }*/
        FlowFieldManager.calcDistanceForEveryNode(parent.getCenterX(),
                parent.getCenterY());

        touchpad.set(parent.play.controller.MovePercentX, parent.play.controller.MovePercentY);
        touchpad2.set(parent.play.controller.TurnPercentX, parent.play.controller.TurnPercentY);

        if(touchpad2.angle() != 0) parent.setRotation(touchpad2.angle());
        else if(touchpad.angle() != 0) parent.setRotation(touchpad.angle());

        // Shoot
        if(!touchpad2.isZero() || parent.play.controller.shoot && parent.weapons[VirtualController.ACTUAL_WEAPON] > 0) {
            Vector2 MuzzlePosAux = MathStatic.RotateVector2(parent.MuzzlePos, parent.getRotation(), aux.set(parent.getCenterX(), parent.getCenterY()));
            switch (VirtualController.ACTUAL_WEAPON){
                case Weapons.ROCKET:
                    if(System.currentTimeMillis() - parent.play.controller.lastTimeShot > Weapons.ROCKET_DELAY){
                        RocketBullet bullet = new RocketBullet(parent.play, parent.world, MuzzlePosAux.x, MuzzlePosAux.y, parent.getRotation());
                        parent.play.bg.addActor(bullet);
                        parent.weapons[VirtualController.ACTUAL_WEAPON]--;
                        parent.play.controller.lastTimeShot = System.currentTimeMillis();
                    }
                    break;
                case Weapons.GRENADE:
                    if(System.currentTimeMillis() - parent.play.controller.lastTimeShot > Weapons.GRENADE_DELAY){
                        GrenadeBullet bullet = new GrenadeBullet(parent.play, parent.world, MuzzlePosAux.x,MuzzlePosAux.y, parent.getRotation());
                        parent.play.bg.addActor(bullet);
                        parent.weapons[VirtualController.ACTUAL_WEAPON]--;
                        if(parent.weapons[VirtualController.ACTUAL_WEAPON] == 0) parent.region = parent.atlas.findRegion(Constants.character + "_punch");
                        parent.play.controller.lastTimeShot = System.currentTimeMillis();
                    }
                    break;
                case Weapons.SHOTGUN:
                    if(System.currentTimeMillis() - parent.play.controller.lastTimeShot > Weapons.SHOTGUN_DELAY){
                        ShotgunBullet bullet = new ShotgunBullet(parent.world, MuzzlePosAux.x,MuzzlePosAux.y, parent.getRotation());
                        parent.play.bg.addActor(bullet);
                        parent.weapons[VirtualController.ACTUAL_WEAPON]--;
                        parent.play.controller.lastTimeShot = System.currentTimeMillis();
                    }
                    break;
                case Weapons.PISTOL:
                    if(System.currentTimeMillis() - parent.play.controller.lastTimeShot > Weapons.PISTOL_DELAY){
                        PistolBullet bullet = new PistolBullet(parent.world, MuzzlePosAux.x,MuzzlePosAux.y, parent.getRotation());
                        parent.play.bg.addActor(bullet);
                        parent.weapons[VirtualController.ACTUAL_WEAPON]--;
                        parent.play.controller.lastTimeShot = System.currentTimeMillis();
                    }
                    break;
                case Weapons.UZI:
                    if(System.currentTimeMillis() - parent.play.controller.lastTimeShot > Weapons.UZI_DELAY) {
                        UziBullet bullet = new UziBullet(parent.world, MuzzlePosAux.x,MuzzlePosAux.y, parent.getRotation());
                        parent.play.bg.addActor(bullet);
                        parent.weapons[VirtualController.ACTUAL_WEAPON]--;
                        parent.play.controller.lastTimeShot = System.currentTimeMillis();
                    }
                    break;
            }

        }

        parent.body.setTransform(parent.body.getPosition().x, parent.body.getPosition().y, (float) Math.toRadians(parent.getRotation()));
        parent.body.setLinearVelocity(touchpad.x *parent.SPEED * dt , touchpad.y * parent.SPEED *dt);
        parent.setPosition(parent.body.getPosition().x - parent.getWidth()/2, parent.body.getPosition().y - parent.getHeight()/2);

        if(parent.setToDestroy) parent.remove();
    }

    @Override
    public void Exit() {

    }
}
