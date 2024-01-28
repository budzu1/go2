package com.tp.goserver;

public class WaitingState implements GoGameState {
    @Override
    public void addOpponent(GoGame game, String opponent) {
        // Add logic to add opponent
        game.setState(new BlackState());
    }

    @Override
    public void addBot(GoGame game) {
        // Add logic to add bot
        game.setState(new BlackState());
    }

    @Override
    public void addMove(GoGame game, int row, int col, String login) {
        throw new IllegalStateException("Cannot add move in waiting state");
    }

    @Override
    public void end(GoGame game) {
        throw new IllegalStateException("Cannot end game in waiting state");
    }

    @Override
    public boolean ifCanChange() {
        return false;
    }
}
