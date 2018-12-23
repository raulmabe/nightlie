package com.nameless.game.actors.items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;
import com.nameless.game.MathStatic;
import com.nameless.game.WeaponsInfo;
import com.nameless.game.actors.enemies.Zombie;

/**
 * Created by Raul on 02/07/2017.
 */
public class UziBullet extends BasicBullet {

    private ShapeRenderer shaper;
    private World world;

    private Vector2 p1, p2;

    public UziBullet(World world, float x, float y, float angle) {
        super(WeaponsInfo.UZI_DAMAGE,24,.25f, .25f);
        this.world = world;

        shaper = new ShapeRenderer();
        shaper.setColor(Color.YELLOW);

        setRotation(angle);
        setPosition(x,y);
        p1 = new Vector2(x,y);
        p2 = MathStatic.RotateVector2(new Vector2(getX()+RANGE, getY()), angle, p1);

        setOrigin(getWidth()/2,getHeight()/2);

        setRaycast();

        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task(){
            @Override
            public void run() {
                dispose();
            }
        }, .05f);
    }

    private void setRaycast(){
        RayCastCallback callback = new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                if(fixture.getBody().getUserData() instanceof Zombie){
                    ((Zombie) fixture.getBody().getUserData()).TakeDamage(DAMAGE, MathStatic.V2minusV2(p2,p1).nor());
                }
                //p2 = point;
                return fraction;
            }
        };
        world.rayCast(callback, p1, p2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();

        shaper.setProjectionMatrix(batch.getProjectionMatrix());
        shaper.begin(ShapeRenderer.ShapeType.Line);
        shaper.line(p1,p2);
        shaper.end();

        batch.begin();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        p1.x = getX();
        p1.y = getY();
    }

    public void dispose(){
        shaper.dispose();
        remove();
    }
}

