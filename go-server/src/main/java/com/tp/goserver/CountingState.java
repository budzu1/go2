package com.tp.goserver;

public class CountingState implements GoGameState {

    @Override
    public void addOpponent(GoGame game, String opponent) {
        throw new IllegalStateException("No operations allowed in counting state");
    }

    @Override
    public void addBot(GoGame game) {
        throw new IllegalStateException("No operations allowed in counting state");
    }

    @Override
    public void addMove(GoGame game, int row, int col, String login) {
        throw new IllegalStateException("No operations allowed in counting state");
    }

    @Override
    public void end(GoGame game) {
        throw new IllegalStateException("No operations allowed in counting state");
    }

    @Override
    public boolean ifCanChange() {
        return false;
    }

    @Override
    public void pass(GoGame game, String login) {
        throw new IllegalStateException("Cannot end game in waiting state");
    }

    @Override
    public void giveUp(GoGame game, String login) {
        // TODO Auto-generated method stub

    }

    @Override
    public void pContinue(GoGame game, String login) {
        // TODO Auto-generated method stub

    }

}
