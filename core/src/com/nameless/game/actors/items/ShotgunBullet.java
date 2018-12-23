package com.nameless.game.actors.items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.nameless.game.MathStatic;
import com.nameless.game.WeaponsInfo;
import com.nameless.game.actors.enemies.Zombie;

public class ShotgunBullet extends BasicBullet {

    private ShapeRenderer shaper;
    private World world;

    private Vector2 p1;
    private Vector2[] p2;

    public ShotgunBullet(World world, float x, float y, float angle) {
        super( WeaponsInfo.SHOTGUN_DAMAGE,10,.25f, .25f);
        this.world = world;

        shaper = new ShapeRenderer();
        shaper.setColor(Color.YELLOW);

        setRotation(angle);
        setPosition(x,y);
        p1 = new Vector2(x,y);
        p2 = new Vector2[6];
        angle -= p2.length/2;
        for(int i = 0; i < p2.length; ++i){
            p2[i] =  MathStatic.RotateVector2(new Vector2(getX()+RANGE, getY()), angle, p1);
            angle += 1;
        }

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
                    ((Zombie) fixture.getBody().getUserData()).TakeDamage(DAMAGE, MathStatic.V2minusV2(point,p1).nor());
                }
                return fraction;
            }
        };

        for(int i = 0; i < p2.length; ++i){
            world.rayCast(callback, p1, p2[i]);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();
        shaper.setProjectionMatrix(batch.getProjectionMatrix());
        shaper.begin(ShapeRenderer.ShapeType.Line);
        for(int i = 0; i < p2.length; ++i){
            shaper.line(p1,p2[i]);
        }
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
        remove();
        shaper.dispose();
    }
}
