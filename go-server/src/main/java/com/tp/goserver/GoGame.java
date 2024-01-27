package com.tp.goserver;

public class GoGame {

    private GoGameState currentState;

    public GoGame() {
        currentState = new WaitingState();
    }

    public void setState(GoGameState state) {
        this.currentState = state;
    }

    // Delegate methods to the current state
    public void addOpponent(String opponent) {
        currentState.addOpponent(this, opponent);
    }

    public void addBot() {
        currentState.addBot(this);
    }

    public void addMove(String move) {
        currentState.addMove(this, move);
    }

    public void end() {
        currentState.end(this);
    }
}
