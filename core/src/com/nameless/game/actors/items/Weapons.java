package com.nameless.game.actors.items;

import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class Weapons {

    ConcurrentHashMap<Integer, Integer> weapons;

    public Weapons() {
        weapons = new ConcurrentHashMap<Integer, Integer>();
    }

    public void addWeapon(int i, int capacity){
        weapons.putIfAbsent(i,capacity);
    }

    public void fillAmmo(int i, int capacity){
        weapons.replace(i, capacity);
    }

    public void removeAmmo(int i, int quantity){
        weapons.replace(i, weapons.get(i)-quantity);
    }

    public boolean isWeapon(int i){
        return weapons.containsKey(i);
    }

    public int getAmmo(int i){
        if(!weapons.containsKey(i)) return Integer.MAX_VALUE;
        else return weapons.get(i);
    }
}
