package com.tp.goserver;

public interface GoGameState {

    void addOpponent(GoGame game, String opponent);

    void addBot(GoGame game);

    void addMove(GoGame game, int row, int col, String login);

    void end(GoGame game);

    public boolean ifCanChange();
}
