package com.tp.goserver;

public interface GoGameState {

    void addOpponent(GoGame game, String opponent);

    void addBot(GoGame game);

    void addMove(GoGame game, String move);

    void end(GoGame game);
}
