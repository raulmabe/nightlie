package com.nameless.game.actors.items;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;


/**
 * Created by Raul on 05/07/2017.
 */
public abstract class BasicBullet extends Actor{
    public float DAMAGE;
    protected float RANGE;
    public float NormalResistance;
    public float TangentResistance;
    public boolean setToDestroy;

    public BasicBullet(float DAMAGE, float RANGE, float normalResistance, float tangentResistance) {
        this.DAMAGE = DAMAGE;
        this.RANGE = RANGE;
        NormalResistance = normalResistance;
        TangentResistance = tangentResistance;
        setToDestroy = false;
    }

    public float getNormalResistance() {
        return NormalResistance;
    }

    public float getTangentResistance() {
        return TangentResistance;
    }

    // In act method:

    //        body.setTransform(body.getPosition().x, body.getPosition().y, (float) Math.toRadians(angle-90));
//        body.applyLinearImpulse(new Vector2(SPEED * (float) Math.sin(Math.toRadians(-angle+90)),
//                SPEED * (float) Math.cos(Math.toRadians(angle-90))), body.getPosition(), true);
//
//        setRotation((float) Math.toDegrees(body.getAngle()));
//        setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
}
