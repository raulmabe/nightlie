package com.nameless.game.actors.states;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;
import com.nameless.game.MathStatic;
import com.nameless.game.actors.Character;
import com.nameless.game.actors.enemies.Zombie;
import com.nameless.game.actors.enemies.FlowFieldState;
import com.nameless.game.actors.player.DefaultPlayerState;
import com.nameless.game.actors.player.Player;

public class InjuredState implements IState{

    private Character parent;
    private float enterTime;

    private TextureRegion previousRegion;

    @Override
    public void Enter(Character parent) {
        if(parent instanceof Zombie){
            previousRegion = ((Zombie) parent).region;
            ((Zombie) parent).region = ((Zombie) parent).atlas.findRegion("zombie" + ((Zombie) parent).Type + "_stand");
        }
        this.parent = parent;
        enterTime = TimeUtils.nanoTime();
    }

    @Override
    public void Update(float dt) {
        parent.body.setLinearVelocity(MathStatic.clampV2(parent.body.getLinearVelocity(), -5, 5));
        if(parent instanceof Zombie) if(((Zombie) parent).direction.angle() != 0) parent.setRotation(((Zombie) parent).direction.angle());
        parent.setPosition(parent.body.getPosition().x - parent.getWidth()/2, parent.body.getPosition().y - parent.getHeight()/2);
        if(TimeUtils.nanoTime() - enterTime > 200000000){
            if(parent instanceof Player) parent.ChangeState(new DefaultPlayerState());
            else if(parent instanceof Zombie) parent.ChangeState(new FlowFieldState());
        }
    }

    @Override
    public void Exit() {
        if(parent instanceof Zombie){
            ((Zombie) parent).region = previousRegion;
        }
    }
}
