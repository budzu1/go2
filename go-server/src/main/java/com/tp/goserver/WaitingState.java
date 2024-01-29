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
    public void assume(GoGame game, String login) {
        if (login == game.getBlack() && game.isWhiteAssume() || login == game.getWhite() && game.isBlackAssume()) {
            // tutaj rozstrzygnbiecie a pózniej wpisz do winner login zwyciezcy
        } else if (login == game.getBlack()) {
            game.setBlackAssume(true);
        } else if (login == game.getWhite()) {
            game.setWhiteAssume(true);
        }

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
