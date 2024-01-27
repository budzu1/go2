package com.tp.goserver;

import java.util.ArrayList;

public final class RuleChecker implements IRuleChecker {
    private Board board;
    private int size;

    public boolean ifCanPlace(Board board, int col, int row) {

        if(board.getStones().get(col).get(row)!=Stone.EMPTY)
        {
            return false;
        }

        return true;
    }

    public RuleChecker(int size){
        this.size=size;
        board = new Board(size);
    }

    public Board placeStone(Board board,int col,int row, Stone stone) {
        board.getStones().get(col).set(row, stone);
        return board;
    }
}
