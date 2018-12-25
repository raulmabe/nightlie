package com.nameless.game.actors.items;

import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.nameless.game.*;

public class RocketBullet extends BasicBullet {
    private World world;
    private Body body;

    private ShapeRenderer shaper;

    public Vector2 p1, p2;
    private float angle;

    private RayHandler rayHandler;

    public RocketBullet(RayHandler rayHandler, World world, float x, float y, float angle) {
        super(WeaponsInfo.ROCKET_DAMAGE,2,.25f, .25f);
        this.world = world;
        this.angle = angle;
        this.rayHandler = rayHandler;

        shaper = new ShapeRenderer();


        p1 = new Vector2(x,y);
        p2 = MathStatic.RotateVector2(new Vector2(getX()+1000, getY()), angle, p1);

        setRotation(angle);
        setPosition(x,y);
        setSize(.15f,.15f);
        setOrigin(getWidth()/2,getHeight()/2);
        setBox2d();
    }


    // Set bullet body
    private void setBox2d() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);
        body.setUserData(this);
//        body.setBullet(true);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(getWidth());

        fdef.shape = shape;
        fdef.filter.categoryBits = Constants.BULLET_BIT;
        fdef.filter.maskBits = Constants.OBSTACLES_BIT | Constants.PLAYER_BIT | Constants.ENEMY_BIT | Constants.NEUTRAL_BIT;
        body.createFixture(fdef);
        shape.dispose();
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, getColor().a);
        batch.end();

        shaper.setProjectionMatrix(batch.getProjectionMatrix());
        shaper.begin(ShapeRenderer.ShapeType.Filled);
        shaper.setColor(new Color(1,1,1,1));
        shaper.circle(getX(),getY(),getWidth(), 20);
        shaper.end();

        batch.begin();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        body.setLinearVelocity(new Vector2(delta*800 * (float) Math.sin(Math.toRadians(-angle+90)),
                delta * 800 * (float) Math.cos(Math.toRadians(angle-90))));
        setPosition(body.getPosition().x , body.getPosition().y);
        if(setToDestroy) explode();

    }

    private void explode(){
        setToDestroy = false;
        world.destroyBody(body);
        body = null;

        new Explosion(world, rayHandler, RANGE, WeaponsInfo.GRENADE_DAMAGE/2, getX(), getY());
        dispose();
    }

    private void dispose(){
        remove();
        shaper.dispose();
    }

}
