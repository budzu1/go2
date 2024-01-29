package com.tp.goserver;

public interface GoGameState {

    void addOpponent(GoGame game, String opponent);

    void addBot(GoGame game);

    void addMove(GoGame game, int row, int col, String login);

    void end(GoGame game);

    void pass(GoGame game, String login);

    void giveUp(GoGame game, String login);

    void pContinue(GoGame game, String login);

    void assume(GoGame game, String login);

    public boolean ifCanChange();
}
