package com.tp.goserver;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class BlackState implements GoGameState {

    @Autowired
    private SimpMessagingTemplate template;

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
        if (!game.getRules().ifCanPlace(game.getBoard(), row, col, Stone.BLACK)) {
            return;
        }

        game.setBoard(game.getRules().placeStone(game.getBoard(), row, col, Stone.BLACK));

        template.convertAndSend("/topic/" + game.getId(), game.getBoard().prepareToSend());

        game.setState(new WhiteState());
    }

    @Override
    public void end(GoGame game) {
        game.setState(new CountingState());
    }
}
