package com.tp.goserver;

public class PauseState implements GoGameState {

    @Override
    public void addBot(GoGame game) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addMove(GoGame game, int row, int col, String login) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addOpponent(GoGame game, String opponent) {
        // TODO Auto-generated method stub

    }

    @Override
    public void end(GoGame game) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean ifCanChange() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void pass(GoGame game, String login) {
        // TODO Auto-generated method stub

    }

    @Override
    public void giveUp(GoGame game, String login) {
        if (login.equals(game.getBlack())) {
            game.setWinner(game.getWhite());
        } else if (login.equals(game.getWhite())) {
            game.setWinner(game.getBlack());
        }

        game.setState(new EndState());

    }

    @Override
    public void pContinue(GoGame game, String login) {
        if (login.equals(game.getBlack())) {
            game.setIfPassed(false);
            game.setState(new WhiteState());
        } else if (login.equals(game.getWhite())) {
            game.setIfPassed(false);
            game.setState(new BlackState());
        }
    }

}
