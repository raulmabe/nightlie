package com.nameless.game;

public interface ISubject {

    void attach(IObserver o);

    void dettach(IObserver o);

    void sendMessage();

    public enum type{
        PLAYER_DEAD,ZOMBIE_DEAD, ALARM_DIA
    }
}
