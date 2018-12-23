package com.nameless.game.actors.items;

import java.util.concurrent.ConcurrentHashMap;

public class Weapons {

    private ConcurrentHashMap<Integer, Integer> ammo;
    private ConcurrentHashMap<Integer, Integer> level;

    public Weapons() {
        ammo = new ConcurrentHashMap<Integer, Integer>();
        level = new ConcurrentHashMap<Integer, Integer>();
    }

    public void addWeapon(int i, int capacity){
        ammo.putIfAbsent(i,capacity);
        level.putIfAbsent(i,1);
    }

    public void fillAmmo(int i, int capacity){
        ammo.replace(i, capacity * level.get(i));
    }

    public void removeAmmo(int i, int quantity){
        ammo.replace(i, ammo.get(i)-quantity);
    }

    public boolean isWeapon(int i){
        return ammo.containsKey(i);
    }

    public int getLevel(int i){
        if(!level.containsKey(i)) return 0;
        else return level.get(i);
    }

    public int getAmmo(int i){
        if(!ammo.containsKey(i)) return Integer.MAX_VALUE;
        else return ammo.get(i);
    }
}
