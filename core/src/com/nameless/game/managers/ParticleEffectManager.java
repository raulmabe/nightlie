package com.nameless.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.graphics.ParticleEmitterBox2D;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.nameless.game.*;
import com.nameless.game.maps.BasicMap;

public class ParticleEffectManager extends Actor {


    private ParticleEffectPool lootPool;
    private Array<PooledEffect> lootEffects;

    private ParticleEffectPool firePool;
    private Array<PooledEffect> fireEffects;

    // static variable single_instance of Type Singleton
    private static ParticleEffectManager single_instance = null;

    public ParticleEffectManager() {
        ParticleEffect lootPrototype = MainGame.manager.get("particles/lootEffect");
        for (int i = 0; i < lootPrototype.getEmitters().size; ++i){
            lootPrototype.getEmitters().set(i,new ParticleEmitterBox2D(BasicMap.world,lootPrototype.getEmitters().get(i)));
        }
        lootPool = new ParticleEffectPool(lootPrototype,0,5);
        lootEffects = new Array<PooledEffect>();


        ParticleEffect firePrototype = MainGame.manager.get("particles/fireEffect");
        for (int i = 0; i < firePrototype.getEmitters().size; ++i){
            firePrototype.getEmitters().set(i,new ParticleEmitterBox2D(BasicMap.world,firePrototype.getEmitters().get(i)));
        }
        firePool = new ParticleEffectPool(firePrototype,0,5);
        fireEffects = new Array<PooledEffect>();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        for(ParticleEffectPool.PooledEffect effect : lootEffects) {
            effect.update(delta);
            if(effect.isComplete()) {
                lootEffects.removeValue(effect, true);
                effect.free();
            }
        }
        for(ParticleEffectPool.PooledEffect effect : fireEffects) {
            effect.update(delta);
            if(effect.isComplete()) {
                fireEffects.removeValue(effect, true);
                effect.free();
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        for(ParticleEffectPool.PooledEffect effect : lootEffects) {
            this.setZIndex(50);
            effect.draw(batch);
        }
        for(ParticleEffectPool.PooledEffect effect : fireEffects) {
            this.setZIndex(1);
            effect.draw(batch);
        }
    }

    // static method to create instance of Singleton class
    public static ParticleEffectManager getInstance()
    {
        if (single_instance == null)
            single_instance = new ParticleEffectManager();
        return single_instance;
    }


    public void addParticle(Type type, Vector2 position, Object o){
        if(type == Type.LOOT){
            PooledEffect effect = lootPool.obtain();
            effect.setPosition(position.x, position.y);
            effect.scaleEffect(1/(Constants.PixelsPerMeter*4));
            float temp[] = effect.getEmitters().first().getTint().getColors();
            Color color = (Color) o;
            temp[0] = color.r;
            temp[1] =  color.g;
            temp[2] =  color.b;
            effect.allowCompletion();
            effect.start();
            lootEffects.add(effect);
        }
        else if(type == Type.FIRE){
            PooledEffect effect = firePool.obtain();
            effect.setPosition(position.x, position.y);
            effect.scaleEffect(1/(Constants.PixelsPerMeter));
            effect.getEmitters().get(0).duration /= 2;
            effect.getEmitters().get(1).duration /= 2;
            effect.allowCompletion();
            effect.start();
            fireEffects.add(effect);
        }
    }

    public void clean() {
        lootPool.freeAll(lootEffects);
        firePool.freeAll(fireEffects);
        lootEffects.clear();
        fireEffects.clear();
    }

    public enum Type{
        LOOT,FIRE
    }
}
