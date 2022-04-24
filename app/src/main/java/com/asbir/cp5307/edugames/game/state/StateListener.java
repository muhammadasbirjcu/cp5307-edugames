package com.asbir.cp5307.edugames.game.state;

public interface StateListener {
    void onUpdate(State state);
    void onCorrectAnswer();
}
