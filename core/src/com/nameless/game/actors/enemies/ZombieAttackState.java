package com.nameless.game.actors.enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.utils.TimeUtils;
import com.nameless.game.MathStatic;
import com.nameless.game.actors.Character;
import com.nameless.game.actors.player.Player;
import com.nameless.game.actors.states.IState;

public class ZombieAttackState implements IState {
    public static final float DAMAGE = 10;

    private Zombie parent;

    private float lastRay;

    private Vector2 p1, p2;
    private RayCastCallback callback;

    private TextureRegion previousRegion;

    @Override
    public void Enter(Character parent) {
        this.parent = (Zombie) parent;
        this.parent.stateTime = 0f;
        previousRegion = ((Zombie) parent).region;

        lastRay = TimeUtils.nanoTime() - this.parent.attackAnim.getAnimationDuration()/2;

        callback = new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                if(fixture.getBody().getUserData() instanceof Player){
                    ((Player) fixture.getBody().getUserData()).TakeDamage(DAMAGE, MathStatic.V2minusV2(p2,p1).nor());
                }
                return 1;
            }
        };

        p1 = new Vector2(0,0);
        p2 = new Vector2(0,0);
    }

    @Override
    public void Update(float dt) {
        parent.stateTime += dt;

        TextureRegion currentFrame = parent.attackAnim.getKeyFrame(parent.stateTime, true);
        parent.region = currentFrame;

        p1 = parent.body.getPosition();
        p2.set(parent.target.getX() + parent.target.getWidth()/2,
                parent.target.getY() + parent.target.getHeight()/2);

        if(MathStatic.getDistancePointToPoint(p1,p2) > parent.RANGE && parent.attackAnim.isAnimationFinished(parent.stateTime)){
            parent.ChangeState(new FlowFieldState());
        }

        parent.setRotation(MathStatic.V2minusV2(p2, p1).angle());
        parent.body.setTransform(parent.body.getPosition().x, parent.body.getPosition().y, (float) Math.toRadians(parent.getRotation()));
        parent.setPosition(parent.body.getPosition().x - parent.getWidth()/2, parent.body.getPosition().y - parent.getHeight()/2);

        if(TimeUtils.nanoTime() - lastRay > parent.attackAnim.getAnimationDuration() / MathUtils.nanoToSec && MathStatic.getDistancePointToPoint(p1,p2) <= parent.RANGE){
            lastRay = TimeUtils.nanoTime();
            parent.world.rayCast(callback, p1, p2);
        }
    }

    @Override
    public void Exit() {
        parent.region = previousRegion;
    }
}
