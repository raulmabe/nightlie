package com.nameless.game.actors;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Timer;
import com.nameless.game.Constants;
import com.nameless.game.MathStatic;
import com.nameless.game.actors.states.IState;
import com.nameless.game.actors.states.InjuredState;
import com.nameless.game.managers.ParticleEffectManager;
import com.nameless.game.maps.BasicMap;

public class Character extends Actor {
    public float INI_SPEED;
    public float SPEED;
    public float MAX_HEALTH;
    public float HEALTH;

    public World world;
    public Body body;

    public IState currentState;

    public Blinker blinker;

    public boolean setToDestroy = false;

    private Timer timer;

    public RayHandler rayHandler;
    protected PointLight selfLight;
    protected boolean inFire = false;

    public Character(float SPEED, float HEALTH) {
        this.INI_SPEED = SPEED;
        this.SPEED = SPEED;
        this.HEALTH = HEALTH;
        this.MAX_HEALTH = HEALTH;

        rayHandler = BasicMap.rayHandler;
        world = BasicMap.world;

        blinker = new Blinker();
        timer = new Timer();
        selfLight = new PointLight(rayHandler, 5, new Color(.5f,0,0,.5f), 3, getX(),getY());
        selfLight.setSoftnessLength(0f);
        selfLight.setActive(false);
        selfLight.setContactFilter(Constants.LOW_FURNITURES_BIT, (short) 0x0000, (short) (Constants.OBSTACLES_BIT));
        selfLight.attachToBody(body);
        inFire = false;
    }

    public void setOnFire(){
        inFire = true;
        selfLight.attachToBody(body);
        selfLight.setActive(true);
        ParticleEffectManager.getInstance().addObjectInFire(this);
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                inFire = false;
                selfLight.setActive(false);
                getColor().lerp(Color.WHITE,.6f);
            }
        }, ParticleEffectManager.getInstance().timeFire);
        getColor().lerp(Color.BLACK,.6f);
    }

    public void changeState(IState newState){
        if(currentState != null) currentState.Exit();
        currentState = newState;
        if(currentState != null) currentState.Enter(this);
    }

    public void takeDamage(float value, Vector2 impulse){
        if(!(currentState instanceof InjuredState))
            changeState(new InjuredState());

        HEALTH -= value;
        HEALTH = MathUtils.clamp(HEALTH, 0, 99999999);

        if(HEALTH <= 0){
            selfLight.setActive(false);
            inFire = false;
            setToDestroy = true;
        }

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
        if(inFire) actOnFire();
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

    private void actOnFire(){
        HEALTH -= .1f;

        if(HEALTH <= 0){
            selfLight.setActive(false);
            inFire = false;
            setToDestroy = true;
        }
    }

    public void transformTo(float x, float y){
        body.setTransform(x,y,0);
        setPosition(x,y);
    }

    public boolean isOnFire(){return inFire;}

}
