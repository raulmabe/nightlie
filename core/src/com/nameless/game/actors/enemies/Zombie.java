package com.nameless.game.actors.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pool;
import com.nameless.game.Constants;
import com.nameless.game.actors.Character;
import com.nameless.game.actors.Loot;
import com.nameless.game.screens.Play;

import static com.nameless.game.Constants.PixelsPerMeter;

public class Zombie extends Character implements Pool.Poolable {
    public static final float RANGE = 1.5f;

    public final int Type = MathUtils.random(1,2);

    private Play play;

    public TextureAtlas atlas;
    public TextureRegion region = null;

    public Actor target;
    public Vector2 direction = Vector2.Zero;

    // Attack animation
    public Animation<TextureRegion> attackAnim;
    public Texture attackSheet;
    public float stateTime;

    private boolean canRequestPath;

    public Zombie(Play play, World world, Actor target, float x, float y) {
        super(world, 100,100);
        this.play = play;
        this.target = target;

        atlas = play.game.manager.get("players/characters.atlas");
        region = atlas.findRegion("zombie" + Type + "_hold");
        if(region.getTexture() == null) Gdx.app.log("Zombie", "NULL");
        // Attack animation
        attackSheet = play.game.manager.get("players/anim/zombie" + Type + "_attack.png");
        TextureRegion[][] tmp = TextureRegion.split(attackSheet, attackSheet.getWidth()/3,
                attackSheet.getHeight());
        TextureRegion[] attackFrames = new TextureRegion[3];
        for (int i = 0; i < 3; ++i)
            attackFrames[i] = tmp[0][i];
        attackAnim = new Animation<TextureRegion>(0.15f, attackFrames);
        stateTime = 0f;

        setPosition(x/PixelsPerMeter, y/PixelsPerMeter);
        setSize(region.getRegionWidth()/PixelsPerMeter, region.getRegionHeight()/PixelsPerMeter);
        setOrigin(getWidth()/2,getHeight()/2);

        setBox2d();

        canRequestPath = false;
        setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
        ChangeState(new FlowFieldState());
    }

    private void setBox2d() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);
        body.setUserData(this);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius((getWidth()/2));

        fdef.shape = shape;
        fdef.filter.categoryBits = Constants.ENEMY_BIT;
        fdef.filter.maskBits = Constants.EVERYTHING_BIT;;
        body.createFixture(fdef);
        shape.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(blinker.shouldBlink(Gdx.graphics.getDeltaTime())) return;

        if(region == null) Gdx.app.log("Zombie", "NULL");
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(),
                getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(setToDestroy) remove();
    }

    @Override
    public boolean remove() {
        if(MathUtils.randomBoolean( .15f)){
            Loot loot = new Loot(play.game,world, getX(), getY());
            play.bg.addActor(loot);
        }
        world.destroyBody(body);
        if (currentState != null) currentState.Exit();
        currentState = null;
        play.waveSpawnManager.zombies.remove(this);
        return super.remove();
    }

    public Vector2 getPosition(){
        return new Vector2(getX(), getY());
    }

    public Vector2 getVelocity(){
        return new Vector2(SPEED,SPEED);
    }

    @Override
    public void reset() {
        HEALTH = 100;
    }

    public void setCanRequestPath(boolean b){canRequestPath = b;}

    public boolean getCanRequestPath() {
        return canRequestPath;
    }
}
