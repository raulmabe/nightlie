package com.nameless.game.actors.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.nameless.game.Constants;
import com.nameless.game.MathStatic;
import com.nameless.game.Weapons;
import com.nameless.game.actors.enemies.Zombie;

import static com.nameless.game.Constants.PixelsPerMeter;

/**
 * Created by Raul on 02/07/2017.
 */
public class UziBullet extends BasicBullet {

    private ShapeRenderer shaper;
    private World world;

    private Vector2 p1, p2, p3;

    public UziBullet(World world, float x, float y, float angle) {
        super(Weapons.UZI_DAMAGE,24,.25f, .25f);
        this.world = world;

        shaper = new ShapeRenderer();
        shaper.setColor(Color.YELLOW);

        setRotation(angle);
        setPosition(x,y);
        p1 = new Vector2(x,y);
        p2 = MathStatic.RotateVector2(new Vector2(getX()+RANGE, getY()), angle, p1);
        p3 = new Vector2(0,0);

        setOrigin(getWidth()/2,getHeight()/2);

        setRaycast();

        float delay = .05f; // seconds
        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                dispose();
            }
        }, delay);
    }

    private void setRaycast(){
        RayCastCallback callback = new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                if(fixture.getBody().getUserData() instanceof Zombie){
                    ((Zombie) fixture.getBody().getUserData()).TakeDamage(DAMAGE, MathStatic.V2minusV2(p2,p1).nor());
                }
//                p2 = point;
                p3 = point;
//                Gdx.app.log("Normal", "" + normal);
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
//
//        shaper.begin(ShapeRenderer.ShapeType.Filled);
//        shaper.setColor(Color.SCARLET);
//        shaper.circle(p3.x, p3.y, 1, 50);
//        shaper.end();

        batch.begin();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        p1 = new Vector2(getX(), getY());
    }

    public void dispose(){
        remove();
        shaper.dispose();
    }
}

