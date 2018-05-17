package com.nameless.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.nameless.game.MathStatic;
import com.nameless.game.actors.states.IState;
import com.nameless.game.actors.states.InjuredState;

public class Character extends Actor {
    public float SPEED;
    public float HEALTH;

    public World world;
    public Body body;

    public IState currentState;

    public Blinker blinker;

    public boolean setToDestroy = false;

    public Character(World world,float SPEED, float HEALTH) {
        this.world = world;
        this.SPEED = SPEED;
        this.HEALTH = HEALTH;

        blinker = new Blinker();
    }

    public void ChangeState(IState newState){
        currentState.Exit();
        currentState = newState;
        currentState.Enter(this);
    }

    public void TakeDamage(float value, Vector2 impulse){
        if(!currentState.equals(InjuredState.class))
            ChangeState(new InjuredState());
        HEALTH -= value;
        HEALTH = MathUtils.clamp(HEALTH, 0, 99999999);
        if(HEALTH <= 0){
            setToDestroy = true;
        }
        blinker.setBlinking(true);
        body.applyLinearImpulse(MathStatic.V2xf(impulse, 3.5f), body.getPosition(), true);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(currentState != null) currentState.Update(delta);
    }

    public float getHEALTH() {
        return HEALTH;
    }

    // Still in process
}
