package com.tp.goserver;

import java.util.ArrayList;

public final class RuleChecker implements IRuleChecker {
    private Board board;
    private int size;

    public boolean ifCanPlace(Board board, int i, int j) {
    public RuleChecker(int size){
        this.size=size;
        board = new Board(size);
    }
    public boolean ifCanPlace() {

        return true;
    }

    public Board placeStone(int col,int row, Stone stone) {
        board.getStones().get(col).set(row, stone);
        return this.board;
    }
}
