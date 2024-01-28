package com.tp.goserver;

public class WhiteState implements GoGameState {

    @Override
    public void addOpponent(GoGame game, String opponent) {
        throw new IllegalStateException("Opponent already added");
    }

    @Override
    public void addBot(GoGame game) {
        throw new IllegalStateException("Bot already added");
    }

    @Override
    public void addMove(GoGame game, int row, int col, String login) {
        // Add logic to add move
        // If move is okay, change state
        game.setState(new BlackState());
    }

    @Override
    public void end(GoGame game) {
        game.setState(new CountingState());
    }
}
