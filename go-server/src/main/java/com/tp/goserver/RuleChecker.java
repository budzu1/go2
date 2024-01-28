package com.tp.goserver;

import java.util.ArrayList;

public class RuleChecker implements IRuleChecker {
    private int size;
    private Liberties liberties;

    public boolean ifCanPlace(Board board, int col, int row, Stone stone) {

        if (board.getStones().get(col).get(row) != Stone.EMPTY) {
            return false;
        }


        return true;
    }

    public RuleChecker(int size) {
        this.size = size;
        liberties = new Liberties(size);
    }

    public Board placeStone(Board board, int col, int row, Stone stone) {



        liberties.updateLiberties(board);
        System.out.println("Before stone removal:\n" + board);
        board = removeStones(board, liberties);
        System.out.println("After stone removal:\n" + board);
        Board toReturn = new Board(board.getStones().size());
        toReturn = board;
        toReturn.getStones().get(col).set(row, stone);
        return toReturn;
    }

    public Board removeStones(Board board, Liberties liberties) {
        for (int col = 0; col < board.getStones().size(); col++) {
            for (int row = 0; row < board.getStones().size(); row++) {
                Stone stone = board.getStones().get(col).get(row);
                if (stone != Stone.EMPTY && liberties.getLiberties(col, row) == 0) {
                    // Sprawdzamy, czy grupa kamieni nie ma oddechów
                    if (!hasLibertyInStoneGroup(board, col, row, stone,
                            new boolean[board.getStones().size()][board.getStones().size()])) {
                        // Jeśli grupa kamieni nie ma oddechów, to usuwamy kamienie
                        removeStoneGroup(board, col, row, stone);
                    }
                }
            }
        }
        return board;
    }

    private boolean hasLibertyInStoneGroup(Board board, int col, int row, Stone stone, boolean[][] visited) {
        if (!isValidPosition(board, col, row) || visited[col][row] || board.getStones().get(col).get(row) != stone) {
            return false;
        }

        visited[col][row] = true;

        return liberties.getLiberties(col, row) > 0
                || hasLibertyInStoneGroup(board, col + 1, row, stone, visited)
                || hasLibertyInStoneGroup(board, col - 1, row, stone, visited)
                || hasLibertyInStoneGroup(board, col, row + 1, stone, visited)
                || hasLibertyInStoneGroup(board, col, row - 1, stone, visited);
    }

    private void removeStoneGroup(Board board, int col, int row, Stone stone) {
        removeStoneGroupRecursive(board, col, row, stone,
                new boolean[board.getStones().size()][board.getStones().size()]);
    }

    private void removeStoneGroupRecursive(Board board, int col, int row, Stone stone, boolean[][] visited) {
        if (!isValidPosition(board, col, row) || visited[col][row] || board.getStones().get(col).get(row) != stone) {
            return;
        }

        visited[col][row] = true;

        board.getStones().get(col).set(row, Stone.EMPTY);

        removeStoneGroupRecursive(board, col + 1, row, stone, visited);
        removeStoneGroupRecursive(board, col - 1, row, stone, visited);
        removeStoneGroupRecursive(board, col, row + 1, stone, visited);
        removeStoneGroupRecursive(board, col, row - 1, stone, visited);
    }

    private boolean isValidPosition(Board board, int col, int row) {
        return col >= 0 && col < board.getStones().size() && row >= 0 && row < board.getStones().size();
    }
}
