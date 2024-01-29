package com.tp.goserver;

import java.util.ArrayList;

public class Replay {

    private ArrayList<Board> boards = new ArrayList<>();
    private IRuleChecker rules;
    private int currBoard = 0;
    private ReplayState state;

    public Replay(int size, ArrayList<Move> array) {
        rules = new RuleChecker(size);
        boards.add(new Board(size));
        state = new ReplayBlack();
        for (int i = 0; i < array.size(); i++) {
            state.addMove(this, array.get(i).getX(), array.get(i).getY(), i);
        }

    }

    public ArrayList<ArrayList<Integer>> getNext() {
        currBoard++;
        return boards.get(currBoard).prepareToSend();
    }

    public ArrayList<ArrayList<Integer>> getPrev() {
        currBoard--;
        return boards.get(currBoard).prepareToSend();
    }

    public ArrayList<Board> getBoards() {
        return boards;
    }

    public void setBoards(ArrayList<Board> boards) {
        this.boards = boards;
    }

    public IRuleChecker getRules() {
        return rules;
    }

    public void setRules(IRuleChecker rules) {
        this.rules = rules;
    }

    public int getCurrBoard() {
        return currBoard;
    }

    public void setCurrBoard(int currBoard) {
        this.currBoard = currBoard;
    }

    public ReplayState getState() {
        return state;
    }

    public void setState(ReplayState state) {
        this.state = state;
    }

    public Board getBoard(int n) {
        return boards.get(n);
    }

    public void addBoard(Board board) {
        boards.add(board);
    }

}
