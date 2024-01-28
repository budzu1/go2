package com.tp.goserver;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

public class GoGame {

    private Board board;

    private GoGameState currentState;

    private String black;

    private String white;

    private IRuleChecker rules;

    Game sqlGame;

    public GoGame(Long id, Game game) {
        currentState = new WaitingState();
        sqlGame = game;
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

    public void addMove(int row, int col, String login) {
        currentState.addMove(this, row, col, login);
    }

    public void end() {
        currentState.end(this);
    }

    public void start() {
        black = sqlGame.getCreator();
        white = sqlGame.getOpponent();
        rules = new RuleChecker(sqlGame.getSize());
        board = new Board(sqlGame.getSize());
        setState(new BlackState());
    }

    public String getBlack() {
        return black;
    }

    public String getWhite() {
        return white;
    }

    public Game getSqlGame() {
        return sqlGame;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public IRuleChecker getRules() {
        return rules;
    }

    public Long getId() {
        return sqlGame.getId();
    }
}
