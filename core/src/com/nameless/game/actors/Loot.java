package com.nameless.game.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.nameless.game.Constants;
import com.nameless.game.MainGame;

import java.io.BufferedOutputStream;

public class Loot extends Actor {
    private final float WIDTH = .7f;
    private final float HEIGHT = .7f;
    private final Color color = new Color(1, .4f,.4f,1);
    private final Color darkerColor = new Color(.7f, 0,0,1);

    private World world;
    private Body body;

    private ShapeRenderer shaper;

    public boolean setToDestroy = false;

    public Loot(World world, float x, float y) {
        this.world = world;
        shaper = new ShapeRenderer();

        setPosition(x,y);
        setSize(WIDTH, HEIGHT);
        setRotation(MathUtils.random(-20,20));
        this.addAction(Actions.forever(Actions.sequence(Actions.sizeBy(-.05f, -.05f, .8f), Actions.sizeBy(.05f, .05f, .8f))));

        setBox2d();
    }

    private void setBox2d() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX() + getWidth()/2, getY()+getHeight()/2);
        bdef.type = BodyDef.BodyType.KinematicBody;
        body = world.createBody(bdef);
        body.setUserData(this);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth()/2, getHeight()/2, new Vector2(0,0), getRotation()*MathUtils.degRad);

        fdef.shape = shape;
        fdef.isSensor = true;
        fdef.filter.categoryBits = Constants.LOW_FURNITURES_BIT;
        fdef.filter.maskBits = Constants.PLAYER_BIT;
        body.createFixture(fdef);
        shape.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();

        shaper.setProjectionMatrix(batch.getProjectionMatrix());
        shaper.begin(ShapeRenderer.ShapeType.Filled);
        shaper.setColor(color);
        shaper.rect(getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        shaper.end();

        shaper.begin(ShapeRenderer.ShapeType.Line);
        shaper.setColor(darkerColor);
        shaper.rect(getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        shaper.end();

        batch.begin();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(setToDestroy) collected();
    }

    private void collected(){
        world.destroyBody(body);
        remove();
    }
}
