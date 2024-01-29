package com.tp.goserver;

import java.util.ArrayList;

public class RuleChecker implements IRuleChecker {
    private final int size;
    private int blackpoints=0;
    private int whitepoints=6;
    private Liberties liberties;

    public boolean ifCanPlace(Board board, int col, int row, Stone stone) {
        Board tempBoard = new Board(size);
        tempBoard=board;
        if (board.getStones().get(col).get(row) != Stone.EMPTY) {
            return false;
        }
        tempBoard= placeStone(board, col, row, stone);

        // Sprawdzamy, czy ruch prowadzi do sytuacji samobójstwa
        if (isSuicidalMove(tempBoard, col, row, stone)) {
            // Jeśli tak, to sprawdzamy, czy zbije przeciwników
            if (!willCaptureOpponents(tempBoard, col, row, stone)) {
                return false;
            }
        }

        ArrayList<ArrayList<Stone>> tempArray = board.getStones();
        tempArray.get(col).set(row, stone);

        // to ma zostać na samym końcu w miare możliwości V !!!!!!!!!

        return true;
    }

    public RuleChecker(int size) {
        this.size = size;
        liberties = new Liberties(size);
    }

    public Board placeStone(Board board, int col, int row, Stone stone) {

        liberties.updateLiberties(board);
        board = removeStones(board, liberties);
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
        if(stone==Stone.BLACK) {
            whitepoints++;
        }
        if(stone==Stone.WHITE) {
            blackpoints++;
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
    private boolean isSuicidalMove(Board board, int col, int row, Stone stone) {
        // Sprawdzamy, czy ruch prowadzi do sytuacji samobójstwa
        board = placeStone(board, col, row, stone);
        Liberties newLiberties = new Liberties(board.getStones().size());
        newLiberties.updateLiberties(board);

        // Jeśli grupa kamieni nie ma oddechów, to ruch jest samobójczy
        return liberties.getLiberties(col, row) == 0;
    }
    private boolean willCaptureOpponents(Board board, int col, int row, Stone stone) {
        // Symulujemy ruch, aby sprawdzić, czy zbije przeciwników
        board = placeStone(board, col, row, stone);

        // Sprawdzamy, czy po ruchu nastąpiło zbicie przeciwników
        boolean opponentsCaptured = false;

        for (int c = 0; c < board.getStones().size(); c++) {
            for (int r = 0; r < board.getStones().size(); r++) {
                Stone opponent = (stone == Stone.BLACK) ? Stone.WHITE : Stone.BLACK;
                if (board.getStones().get(c).get(r) == opponent) {
                    Liberties opponentLiberties = new Liberties(board.getStones().size());
                    opponentLiberties.updateLiberties(board);

                    // Jeśli przeciwnik stracił wszystkie oddechy, to został zbity
                    if (opponentLiberties.getLiberties(c, r) == 0) {
                        opponentsCaptured = true;
                        break;
                    }
                }
            }
            if (opponentsCaptured) {
                break;
            }
        }
        return opponentsCaptured;
    }

    public int getBlackPoints(){
        return blackpoints;
    }
    public int getWhitePoints(){
        return whitepoints;
    }
}
