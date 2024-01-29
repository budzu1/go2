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
    public void assume(GoGame game, String login) {
    }

    @Override
    public void addMove(GoGame game, int row, int col, String login) {
        if (!login.equals(game.getWhite())) {
            return;
        }
        if (!game.getRules().ifCanPlace(game.getBoard(), col, row, Stone.WHITE)) {
            return;
        }

        game.setBoard(game.getRules().placeStone(game.getBoard(), col, row, Stone.WHITE));

        System.out.println("toWhite");
        game.setIfPassed(false);
        game.setState(new BlackState());
    }

    @Override
    public void end(GoGame game) {
        game.setState(new CountingState());
    }

    @Override
    public boolean ifCanChange() {
        return true;
    }

    @Override
    public void pass(GoGame game, String login) {
        if (!login.equals(game.getWhite())) {
            return;
        }

        if (game.isIfPassed()) {
            game.setIfPassed(false);
            game.setState(new PauseState());
            return;
        }

        game.setIfPassed(true);

        System.out.println("toWhite");
        game.setState(new BlackState());
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
        // TODO Auto-generated method stub

    }

}
