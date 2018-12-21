package com.nameless.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Timer;
import com.nameless.game.MathStatic;
import com.nameless.game.actors.states.IState;
import com.nameless.game.actors.states.InjuredState;

public class Character extends Actor {
    public float INI_SPEED;
    public float SPEED;
    public float HEALTH;

    public World world;
    public Body body;

    public IState currentState;

    public Blinker blinker;

    public boolean setToDestroy = false;

    private Timer timer;

    public Character(World world,float SPEED, float HEALTH) {
        this.world = world;
        this.INI_SPEED = SPEED;
        this.SPEED = SPEED;
        this.HEALTH = HEALTH;

        blinker = new Blinker();
        timer = new Timer();
    }

    public void ChangeState(IState newState){
        if(currentState != null) currentState.Exit();
        currentState = newState;
        if(currentState != null) currentState.Enter(this);
    }

    public void TakeDamage(float value, Vector2 impulse){
        if(!(currentState instanceof InjuredState))
            ChangeState(new InjuredState());

        HEALTH -= value;
        HEALTH = MathUtils.clamp(HEALTH, 0, 99999999);

        if(HEALTH <= 0)
            setToDestroy = true;

        this.blinker.setBlinking(true);
        body.applyLinearImpulse(MathStatic.V2xf(impulse, 3.5f), body.getPosition(), true);
        timer.scheduleTask(new Timer.Task(){
            @Override
            public void run() {
                body.setLinearVelocity(new Vector2(0,0));
            }
        }, .5f);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(currentState != null) currentState.Update(delta);
    }

    public float getCenterX(){
        return getX()+getOriginX();
    }

    public float getCenterY(){
        return getY()+getOriginY();
    }

    public float getHEALTH() {
        return HEALTH;
    }

    public void transformTo(float x, float y){
        body.setTransform(x,y,0);
        setPosition(x,y);
    }


}
