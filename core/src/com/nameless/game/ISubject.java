package com.nameless.game;

public interface ISubject {

    void attach(IObserver o);

    void dettach(IObserver o);

    enum type{
        PLAYER_DEAD,ZOMBIE_DEAD, ROUND_FINNISH, ROUND_START, LOOT_PARTICLE
    }
}
