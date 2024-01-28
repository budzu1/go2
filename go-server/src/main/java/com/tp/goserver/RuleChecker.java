package com.tp.goserver;

import java.util.ArrayList;

public class RuleChecker implements IRuleChecker {
    private int size;

    public boolean ifCanPlace(Board board, int col, int row, Stone stone) {

        if (board.getStones().get(col).get(row) != Stone.EMPTY) {
            return false;
        }

        return true;
    }

    public RuleChecker(int size) {
        this.size = size;
    }

    public Board placeStone(Board board, int col, int row, Stone stone) {
        if (ifCanPlace(board, col, row, stone) == true) {
            board.getStones().get(col).set(row, stone);
        }
        return board;
    }
}
