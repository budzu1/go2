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
        if (!login.equals(game.getWhite())) {
            return;
        }
        if (!game.getRules().ifCanPlace(game.getBoard(), col, row, Stone.WHITE)) {
            return;
        }

        game.setBoard(game.getRules().placeStone(game.getBoard(), col, row, Stone.WHITE));

        System.out.println("toWhite");
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
}
