package com.nameless.game.actors.items;

import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.TimeUtils;
import com.nameless.game.Constants;
import com.nameless.game.WeaponsInfo;

public class GrenadeBullet extends BasicBullet {

    private World world;
    private Body body;

    private ShapeRenderer shaper;

    private float time = TimeUtils.nanoTime();

    private RayHandler rayHandler;

    public GrenadeBullet(RayHandler rayHandler, World world, float x, float y, float angle) {
        super( WeaponsInfo.GRENADE_DAMAGE,3,.25f, .25f);
        this.world = world;
        this.rayHandler = rayHandler;

        setRotation(angle);
        setPosition(x,y);
        setSize(.1f,.1f);
        setOrigin(getWidth()/2,getHeight()/2);

        setBox2d();

        shaper = new ShapeRenderer();

        body.applyLinearImpulse(new Vector2(16 * (float) Math.sin(Math.toRadians(-angle+90)),
                16 * (float) Math.cos(Math.toRadians(angle-90))), body.getPosition(), true);
        body.setLinearDamping(2.5f);
    }


    // Set bullet body
    private void setBox2d() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);
        body.setUserData(this);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(getWidth());

        fdef.shape = shape;
        fdef.filter.categoryBits = Constants.BULLET_BIT;
        fdef.filter.maskBits = Constants.EVERYTHING_BIT;
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
        shaper.setColor(new Color(70/255f,79/255f,84/255f,1));
        shaper.circle(getX(),getY(),getWidth(), 10);
        shaper.end();

        shaper.begin(ShapeRenderer.ShapeType.Line);
        shaper.setColor(new Color(46/255f,52/255f,56/255f,1));
        shaper.circle(getX(), getY(), getWidth(), 10);
        shaper.end();

        batch.begin();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        setPosition(body.getPosition().x , body.getPosition().y);
        if((TimeUtils.nanoTime() - time) * MathUtils.nanoToSec > 2.5f){
            explode();
        }
    }

    private void explode(){
        world.destroyBody(body);

        new Explosion(world, rayHandler, RANGE, DAMAGE, getX(), getY());
        dispose();
    }

    private void dispose(){
        remove();
        shaper.dispose();
    }
}
