package com.tp.goserver;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class BlackState implements GoGameState {

    @Autowired
    GameService gameService;

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
        if (!login.equals(game.getBlack())) {
            return;
        }
        if (!game.getRules().ifCanPlace(game.getBoard(), col, row, Stone.BLACK)) {
            return;
        }

        game.setBoard(game.getRules().placeStone(game.getBoard(), col, row, Stone.BLACK));

        gameService.addMove(game.getId(), new Move(row, col));

        System.out.println("toWhite");
        game.setState(new WhiteState());
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
