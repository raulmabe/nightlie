package com.nameless.game.actors.items;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.TimeUtils;
import com.nameless.game.Constants;
import com.nameless.game.MainGame;
import com.nameless.game.MathStatic;
import com.nameless.game.Weapons;
import com.nameless.game.actors.Character;
import com.nameless.game.actors.enemies.Zombie;
import com.nameless.game.actors.player.Player;
import com.nameless.game.managers.ParticleEffectManager;
import com.nameless.game.screens.BasicPlay;
import com.nameless.game.screens.Play;

public class GrenadeBullet extends BasicBullet {

    private World world;
    private Body body;

    private ShapeRenderer shaper;

    // Attack animation
    public Animation<TextureRegion> explodeAnim;
    public Texture explodeSheet;
    public float stateTime;

    private TextureRegion region;

    private float time = TimeUtils.nanoTime();

    private RayHandler rayHandler;

    public GrenadeBullet(RayHandler rayHandler, World world, float x, float y, float angle) {
        super( Weapons.GRENADE_DAMAGE,3,.25f, .25f);
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


        // Explode animation
        explodeSheet = MainGame.manager.get("players/anim/explosion.png");
        TextureRegion[][] tmp = TextureRegion.split(explodeSheet, explodeSheet.getWidth()/5,
                explodeSheet.getHeight());
        TextureRegion[] attackFrames = new TextureRegion[5];
        for (int i = 0; i < 5; ++i)
            attackFrames[i] = tmp[0][i];
        explodeAnim = new Animation<TextureRegion>(1/30f, attackFrames);
        stateTime = 0f;
        region = attackFrames[0];
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
        setSize(region.getRegionWidth()*2/Constants.PixelsPerMeter, region.getRegionHeight()*2/Constants.PixelsPerMeter);
        world.destroyBody(body);

        new Explosion(world, rayHandler, RANGE, DAMAGE, getX(), getY());
        dispose();
    }

    private void dispose(){
        remove();
        shaper.dispose();
    }
}
