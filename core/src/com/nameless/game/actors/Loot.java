package com.nameless.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.nameless.game.*;
import com.nameless.game.managers.ParticleEffectManager;

import java.util.ArrayList;
import java.util.Timer;

public class Loot extends Actor {

    private final float WIDTH = .7f;
    private final float HEIGHT = .7f;
    private final Color color;

    private World world;
    private Body body;

    public boolean setToDestroy = false;

    private Type type;

    private Texture texture;


    public Loot(World world, Type type, float x, float y) {
        this.world = world;
        this.type = getType(type);
        this.setZIndex(50);


        color = new Color();

        switch (this.type){
            case AMMO:
                color.set(Color.OLIVE); // Green
                break;
            case LIFE:
                color.set(Color.CORAL); // Red
                break;
            case WEAPON:
                color.set(Color.valueOf("#006e82")); // Blue
                break;
        }

        setPosition(x,y);
        setSize(WIDTH, HEIGHT);
        setOrigin(WIDTH/2, HEIGHT/2);
        setRotation(MathUtils.random() * 360);
        setColor(color);
        this.addAction(Actions.forever(Actions.sequence(Actions.sizeBy(-.05f, -.05f, .8f), Actions.sizeBy(.05f, .05f, .8f))));

        setBox2d();

        texture = MainGame.manager.get("weapons/loot.png");

    }

    private Type getType(Type type) {
        if(type != Loot.Type.RANDOM) return type;
        else{
            int x = MathUtils.random(100) % 3;
            switch (x){
                case 0:
                    return Loot.Type.AMMO;
                case 1:
                    return Loot.Type.LIFE;
                case 2:
                default:
                    return Loot.Type.WEAPON;

            }
        }
    }

    private void setBox2d() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX() + getWidth()/2, getY()+getHeight()/2);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.linearDamping = 20f;
        bdef.angle = getRotation()*MathUtils.degRad;
        body = world.createBody(bdef);
        body.setUserData(this);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth()/2, getHeight()/2, new Vector2(0,0), getRotation()*MathUtils.degRad);

        fdef.shape = shape;
        //fdef.isSensor = true;
        fdef.filter.categoryBits = Constants.LOW_FURNITURES_BIT;
        fdef.filter.maskBits = Constants.EVERYTHING_BIT; //Constants.PLAYER_BIT;
        body.createFixture(fdef);
        shape.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, getColor().a);
        batch.setColor(color);
        batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation(),
                0,0,texture.getWidth(), texture.getHeight(), false, false);
        batch.setColor(Color.WHITE);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
        setRotation(body.getAngle()*MathUtils.radDeg);
        if(setToDestroy){
            collected();
        }
    }

    private void collected() {
        ParticleEffectManager.getInstance().addParticle(ParticleEffectManager.Type.LOOT, body.getPosition(), color);
        // Add Label
        world.destroyBody(body);
        remove();
    }

    public void applyImpulse(Vector2 impulse) {
        body.applyLinearImpulse(MathStatic.V2xf(impulse, .1f), body.getPosition(), true);
    }

    public enum Type {
        WEAPON, AMMO, LIFE, RANDOM
    }

    public Type getType(){
        return type;
    }
}
