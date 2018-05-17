package com.nameless.game.actors.states;

import com.nameless.game.actors.Character;

public interface IState {

    void Enter(Character parent);

    void Update(float dt);

    void Exit();
}
