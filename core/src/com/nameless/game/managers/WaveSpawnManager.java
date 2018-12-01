package com.nameless.game.managers;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.nameless.game.actors.enemies.Zombie;
import com.nameless.game.screens.Play;

import java.util.ArrayList;

public class WaveSpawnManager {
    public final float TIME_BETWEEN_WAVES = 2000000000;
    public final float TIME_BETWEEN_ZOMBIES_PATH_REQUEST = 200000000;

    private float timeBetweenPathRequest;
    private int index;

    public ArrayList<Zombie> zombies;

    private Play parent;

    private float timeToNextSpawn;
    private int round;

    public WaveSpawnManager(Play parent) {
        this.parent = parent;
        zombies = new ArrayList<Zombie>();
        timeBetweenPathRequest= TimeUtils.nanoTime();
        timeToNextSpawn= TimeUtils.nanoTime();
        round = 0;
    }

    public void update(float delta){
        if(parent.state == parent.GAME_RUNNING && zombies.size() != 0 &&
                TimeUtils.nanoTime() - timeBetweenPathRequest > TIME_BETWEEN_ZOMBIES_PATH_REQUEST) {
            zombieSearchPath();
        }

        if(parent.state != parent.GAME_WAITING)
            timeToNextSpawn = TimeUtils.nanoTime();


        if(zombies.size() == 0 && parent.state != parent.GAME_WAITING ){
            parent.state = parent.GAME_WAITING;
            timeBetweenPathRequest = TimeUtils.nanoTime();
        }
        if(parent.state == parent.GAME_WAITING){
            if(TimeUtils.nanoTime() - timeToNextSpawn > TIME_BETWEEN_WAVES){
                round++;
                //WaveSpawn();
            } else{
                //parent.hud.timeToNextSpawn.setText(""+ (2 - (MathUtils.nanoToSec * (TimeUtils.nanoTime() - timeToNextSpawn))));
                //parent.hud.timeToNextSpawn.setText("" + round);
            }
        }
    }

    private void zombieSearchPath() {
        index++;
        if(index >= zombies.size()) index = 0;

        zombies.get(index).setCanRequestPath(true);
    }

    private void WaveSpawn(){
        float delay = .1f; // seconds
//
        for(int i = 0;i < 10; ++i){  // i < (round*2 + 10)
            Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    Vector2 pos = parent.map.getPositionEnemy();
                    Zombie zombie = new Zombie(parent, parent.map.world, parent.player, pos.x, pos.y);
                    zombies.add(zombie);
                    parent.fg.addActor(zombie);
                }
            }, delay * i);
        }
        parent.state = parent.GAME_RUNNING;

    }

    public void clear(){
        for (int i = 0; i < zombies.size(); ++i) {
            zombies.get(i).remove();
        }
        zombies.clear();
    }
}
