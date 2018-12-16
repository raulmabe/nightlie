package com.nameless.game;

public interface ISubject {

    void attach(IObserver o);

    void dettach(IObserver o);

    public enum type{
        PLAYER_DEAD,ZOMBIE_DEAD, ROUND_FINNISH, ROUND_START
    }
}
