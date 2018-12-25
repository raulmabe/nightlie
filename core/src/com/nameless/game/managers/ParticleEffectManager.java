package com.nameless.game.managers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.graphics.ParticleEmitterBox2D;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.nameless.game.*;
import com.nameless.game.actors.Character;
import com.nameless.game.maps.BasicMap;

public class ParticleEffectManager extends Actor {


    private ParticleEffectPool lootPool;
    private Array<PooledEffect> lootEffects;

    private ParticleEffectPool explosionPool;
    private Array<PooledEffect> explosionEffects;

    private ParticleEffectPool firePool;
    private Array<PooledEffect> fireEffects;
    private Array<Character> fireActors;

    public int timeFire = 5; //seconds

    // static variable single_instance of Type Singleton
    private static ParticleEffectManager single_instance = null;

    public ParticleEffectManager() {
        ParticleEffect lootPrototype = MainGame.manager.get("particles/lootEffect");
        for (int i = 0; i < lootPrototype.getEmitters().size; ++i){
            lootPrototype.getEmitters().set(i,new ParticleEmitterBox2D(BasicMap.world,lootPrototype.getEmitters().get(i)));
        }
        lootPool = new ParticleEffectPool(lootPrototype,0,5);
        lootEffects = new Array<PooledEffect>();


        ParticleEffect explosionPrototype = MainGame.manager.get("particles/explosionEffect");
        for (int i = 0; i < explosionPrototype.getEmitters().size; ++i){
            explosionPrototype.getEmitters().set(i,new ParticleEmitterBox2D(BasicMap.world,explosionPrototype.getEmitters().get(i)));
        }
        explosionPool = new ParticleEffectPool(explosionPrototype,0,5);
        explosionEffects = new Array<PooledEffect>();


        ParticleEffect firePrototype = MainGame.manager.get("particles/fireEffect");
        for (int i = 0; i < firePrototype.getEmitters().size; ++i){
            firePrototype.getEmitters().set(i,new ParticleEmitterBox2D(BasicMap.world,firePrototype.getEmitters().get(i)));
        }
        firePool = new ParticleEffectPool(firePrototype,0,5);
        fireEffects = new Array<PooledEffect>();
        fireActors = new Array<Character>();
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
        for(ParticleEffectPool.PooledEffect effect : explosionEffects) {
            effect.update(delta);
            if(effect.isComplete()) {
                explosionEffects.removeValue(effect, true);
                effect.free();
            }
        }
        for(int i = 0; i < fireEffects.size; ++i) {
            PooledEffect effect = fireEffects.get(i);
            effect.setPosition(fireActors.get(i).getX()+fireActors.get(i).getWidth()/2,
                    fireActors.get(i).getY() + fireActors.get(i).getHeight()/2);
            effect.getEmitters().first().getRotation().setHighMax(fireActors.get(i).getRotation());
            effect.getEmitters().first().getWind().setHighMax(2 * MathUtils.sin(fireActors.get(i).getRotation() * 360));
            effect.update(delta);
            if(effect.isComplete() || fireActors.get(i).setToDestroy) {
                fireEffects.removeValue(effect, true);
                fireActors.removeValue(fireActors.get(i), true);
                effect.free();
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        for(ParticleEffectPool.PooledEffect effect : lootEffects) {
            this.setZIndex(0);
            effect.draw(batch);
        }
        for(ParticleEffectPool.PooledEffect effect : explosionEffects) {
            this.setZIndex(this.getParent().getChildren().size-1);
            effect.draw(batch);
        }
        for(ParticleEffectPool.PooledEffect effect : fireEffects) {
            this.setZIndex(this.getParent().getChildren().size-1);
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
        else if(type == Type.EXPLOSION){
            PooledEffect effect = explosionPool.obtain();
            effect.setPosition(position.x, position.y);
            effect.scaleEffect(1/(Constants.PixelsPerMeter));
            effect.getEmitters().get(0).duration /= 2;
            effect.getEmitters().get(1).duration /= 2;
            effect.allowCompletion();
            effect.start();
            explosionEffects.add(effect);
        }
    }

    public void addObjectInFire(Character character){
        if(fireActors.contains(character, true)) return;
        PooledEffect effect = firePool.obtain();
        effect.setPosition(character.getX(),character.getY());
        effect.scaleEffect(1/(Constants.PixelsPerMeter));
        effect.getEmitters().first().getWind().setHighMax(10 * MathUtils.sin(character.getRotation()));
        effect.getEmitters().first().getRotation().setHighMax(character.getRotation());
        effect.setDuration(timeFire * 1000);
        effect.allowCompletion();
        effect.start();
        fireEffects.add(effect);
        fireActors.add(character);
    }

    public void clean() {
        lootPool.freeAll(lootEffects);
        explosionPool.freeAll(explosionEffects);
        firePool.freeAll(fireEffects);
        lootEffects.clear();
        explosionEffects.clear();
        fireEffects.clear();
        fireActors.clear();
    }

    public enum Type{
        LOOT, EXPLOSION
    }
}
