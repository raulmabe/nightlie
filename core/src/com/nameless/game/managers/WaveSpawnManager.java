package com.nameless.game.managers;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.nameless.game.Constants;
import com.nameless.game.IObserver;
import com.nameless.game.ISubject;
import com.nameless.game.actors.enemies.Zombie;
import com.nameless.game.maps.LevelManager;
import com.nameless.game.pathfinding.Node;
import com.nameless.game.screens.Play;

import java.util.ArrayList;

public class WaveSpawnManager implements IObserver, ISubject{
    private ArrayList<IObserver> observers;
    private final float TIME_BETWEEN_WAVES = 2000000000;

    private Timer timer;

    public ArrayList<Zombie> zombies;

    private Play parent;

    private float timeToNextSpawn;
    private int round;
    private boolean alreadyFinnished = false;

    public WaveSpawnManager(Play parent) {
        this.parent = parent;
        observers = new ArrayList<IObserver>();
        zombies = new ArrayList<Zombie>();
        timeToNextSpawn= TimeUtils.nanoTime();
        round = 0;
        timer = new Timer();
    }

    public void update(float delta){
        if(parent.state != parent.GAME_WAITING)
            timeToNextSpawn = TimeUtils.nanoTime();


        if(zombies.isEmpty() && parent.state != parent.GAME_WAITING ){
            parent.state = parent.GAME_WAITING;
        }

        if(parent.state == parent.GAME_WAITING){
            if(TimeUtils.nanoTime() - timeToNextSpawn > TIME_BETWEEN_WAVES*2)
                WaveSpawn();

        }
    }

    private void WaveSpawn(){
        round++;
        alreadyFinnished = false;
        sendMessage(type.ROUND_START);

        parent.state = parent.GAME_RUNNING;

        final float[] delay = {MathUtils.random(.1f, 1f)}; // seconds

        System.out.println("Round");

        timer.scheduleTask(new Timer.Task(){
            @Override
            public void run() {
                float ang = MathUtils.random() * 360;
                float x = parent.player.getCenterX() + Constants.RENDER_WIDTH * MathUtils.sin(ang);
                float y = parent.player.getCenterY()+ Constants.RENDER_WIDTH  * MathUtils.cos(ang);

                while(x < 0 || y < 0 || x > LevelManager.WIDTH_IN_PIXELS || y > LevelManager.HEIGHT_IN_PIXELS ||
                        LevelManager.graph.getNodeByXYFloat(parent.player.getCenterX() + Constants.RENDER_WIDTH * MathUtils.sin(ang),
                        parent.player.getCenterY()+ Constants.RENDER_WIDTH  * MathUtils.cos(ang)).type != Node.Type.REGULAR){
                    ang = MathUtils.random() * 360;
                    x = parent.player.getCenterX() + Constants.RENDER_WIDTH * MathUtils.sin(ang);
                    y = parent.player.getCenterY()+ Constants.RENDER_WIDTH  * MathUtils.cos(ang);
                }

                Zombie zombie = new Zombie(parent, parent.player,x,y);
                zombies.add(zombie);
                zombie.attach(WaveSpawnManager.this);
                parent.fg.addActor(zombie);
                delay[0] = MathUtils.random(.1f, 3f); // seconds
                System.out.println("Zombies: " + zombies.size());
            }
        }, 4, delay[0], (round*2 + 30));
    }

    @Override
    public void handleMessage(Object obj, ISubject.type type) {
        if(type == ISubject.type.ZOMBIE_DEAD){
            zombies.remove(obj);
            if(zombies.size() < MathUtils.random(4,12) && !alreadyFinnished){
                sendMessage(ISubject.type.ROUND_FINNISH);
                alreadyFinnished = true;
            }
        }
    }

    @Override
    public void attach(IObserver o) {
        if(!observers.contains(o)) observers.add(o);
    }

    @Override
    public void dettach(IObserver o) {
        observers.remove(o);
    }

    private void sendMessage(ISubject.type type) {
        for (IObserver o : observers) {
            o.handleMessage(round, type);
        }
    }

    public void clearScene(){
        timer.clear();
        sendMessage(type.ROUND_FINNISH);

        for (int i = 0; i < zombies.size(); ++i) {
            zombies.get(i).remove();
        }
        zombies.clear();
    }

    public void pause() {
        timer.stop();
    }

    public void resume(){
        timer.start();
    }
}
