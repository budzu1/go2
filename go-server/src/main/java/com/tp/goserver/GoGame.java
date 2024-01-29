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

    private boolean ifPassed;

    private String winner;

    private boolean blackAssume;

    private boolean whiteAssume;

    Game sqlGame;

    public GoGame(Long id, Game game) {
        currentState = new WaitingState();
        sqlGame = game;
        board = new Board(sqlGame.getSize());
    }

    public boolean isBlackAssume() {
        return blackAssume;
    }

    public void setBlackAssume(boolean blackAssume) {
        this.blackAssume = blackAssume;
    }

    public boolean isWhiteAssume() {
        return whiteAssume;
    }

    public void setWhiteAssume(boolean whiteAssume) {
        this.whiteAssume = whiteAssume;
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

    public void start(Game game) {
        sqlGame = game;
        black = sqlGame.getCreator();
        white = sqlGame.getOpponent();
        System.out.println(sqlGame.getSize());
        rules = new RuleChecker(sqlGame.getSize());
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

    public boolean isIfPassed() {
        return ifPassed;
    }

    public void setIfPassed(boolean ifPassed) {
        this.ifPassed = ifPassed;
    }

    public boolean ifCanChange() {
        return currentState.ifCanChange();
    }

    public void pass(String login) {
        currentState.pass(this, login);
    }

    public void giveUp(String login) {
        currentState.giveUp(this, login);
    }

    public void pContinue(String login) {
        currentState.pContinue(this, login);
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public int getWhitePoints() {
        return rules.getWhitePoints();
    }

    public int getBlackPoints() {
        return rules.getBlackPoints();
    }

    public void assume(String login) {
        currentState.assume(this, login);

    }
}
