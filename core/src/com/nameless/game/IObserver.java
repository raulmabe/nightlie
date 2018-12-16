package com.nameless.game;

public interface IObserver {
    void handleMessage(Object o, ISubject.type type);
}
