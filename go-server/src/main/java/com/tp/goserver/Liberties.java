package com.tp.goserver;

import java.util.ArrayList;

public class Liberties {
    private ArrayList<ArrayList<Integer>> liberties;

    public Liberties(int size) {
        this.liberties = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ArrayList<Integer> tempArray = new ArrayList<>();
            for (int j = 0; j <size; j++) {
                tempArray.add(0);
            }
            liberties.add(tempArray);
        }
    }


    public void addLiberties(Board board, int col, int row) {
        checkEmptyIntersections(board, col, row);
    }

    private void checkEmptyIntersections(Board board, int col, int row) {
        int i = 1;
        int j = 1;
        if (isValidPosition(board, col + i, row) && board.getStones().get(col + i).get(row) == Stone.EMPTY) {
            liberties.get(col).set(row, j);
            j++;
        }
        if (isValidPosition(board, col - i, row) && board.getStones().get(col - i).get(row) == Stone.EMPTY) {
            liberties.get(col).set(row, j);
            j++;
        }
        if (isValidPosition(board, col, row + i) && board.getStones().get(col).get(row + i) == Stone.EMPTY) {
            liberties.get(col).set(row, j);
            j++;
        }
        if (isValidPosition(board, col, row - i) && board.getStones().get(col).get(row - i) == Stone.EMPTY) {
            liberties.get(col).set(row, j);
        }
    }

    private void stoneChainLiberties(Board board, int col, int row) {
        int i = 1;
        if (isValidPosition(board, col + i, row) && board.getStones().get(col + i).get(row) == board.getStones().get(col).get(row)) {
            int libertiesValue = getLiberties(col, row);
            int groupLiberties = getLiberties(col + i, row);
            libertiesValue = Math.max(libertiesValue, groupLiberties);
            setLiberties(board, col, row, libertiesValue);
            setLiberties(board, col + i, row, libertiesValue);
        }
        if (isValidPosition(board, col - i, row) && board.getStones().get(col - i).get(row) == board.getStones().get(col).get(row)) {
            int libertiesValue = getLiberties(col, row);
            int groupLiberties = getLiberties(col - i, row);
            libertiesValue = Math.max(libertiesValue, groupLiberties);
            setLiberties(board, col, row, libertiesValue);
            setLiberties(board, col - i, row, libertiesValue);
        }
        if (isValidPosition(board, col, row + i) && board.getStones().get(col).get(row + i) == board.getStones().get(col).get(row)) {
            int libertiesValue = getLiberties(col, row);
            int groupLiberties = getLiberties(col, row + i);
            libertiesValue = Math.max(libertiesValue, groupLiberties);
            setLiberties(board, col, row, libertiesValue);
            setLiberties(board, col, row + i, libertiesValue);
        }
        if (isValidPosition(board, col, row - i) && board.getStones().get(col).get(row - i) == board.getStones().get(col).get(row)) {
            int libertiesValue = getLiberties(col, row);
            int groupLiberties = getLiberties(col, row - i);
            libertiesValue = Math.max(libertiesValue, groupLiberties);
            setLiberties(board, col, row, libertiesValue);
            setLiberties(board, col, row - i, libertiesValue);
        }
    }

    private void setLiberties(Board board, int col, int row, int libertiesValue) {
        liberties.get(col).set(row, libertiesValue);
    }

    private boolean isValidPosition(Board board, int col, int row) {
        return col >= 0 && col < board.getStones().size() && row >= 0 && row < board.getStones().size();
    }

    public ArrayList<ArrayList<Integer>> updateLiberties(Board board) {
        for (int i = 0; i < board.getStones().size(); i++) {
            for (int j = 0; j < board.getStones().size(); j++) {
                liberties.get(j).set(i, 0);
            }
        }
        for (int i = 0; i < board.getStones().size(); i++) {
            for (int j = 0; j < board.getStones().size(); j++) {
                addLiberties(board, j, i);
            }
        }
        return liberties;
    }

    public int getLiberties(int col, int row) {
        return liberties.get(col).get(row);
    }
}
