package com.tp.goserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RuleCheckerTest {

    private RuleChecker ruleChecker;
    private Board board;

    @BeforeEach
    public void setUp() {
        int size = 19; // Rozmiar planszy
        ruleChecker = new RuleChecker(size);
        board = new Board(size);
    }

    @Test
    public void testIfCanPlaceWhenEmptyCell() {
        int col = 1;
        int row = 1;
        assertTrue(ruleChecker.ifCanPlace(board, col, row, Stone.BLACK));
    }

    @Test
    public void testIfCanPlaceWhenNotEmptyCell() {
        int col = 2;
        int row = 2;

        Stone stone = Stone.BLACK;
        board = ruleChecker.placeStone(board, col, row, stone);

        assertFalse(ruleChecker.ifCanPlace(board, col, row, Stone.BLACK));
    }

    @Test
    public void testPlaceStoneWhenValidMove() {
        int col = 3;
        int row = 3;
        Stone stone = Stone.BLACK;

        Board newBoard = ruleChecker.placeStone(board, col, row, stone);

        // Sprawdź, czy kamień został poprawnie umieszczony
        assertEquals(stone, newBoard.getStones().get(col).get(row));
    }

    @Test
    public void testPlaceStoneWhenInvalidMove() {
        int col = 4;
        int row = 4;

        // Umieść kamień na planszy
        Stone existingStone = Stone.BLACK;
        board = ruleChecker.placeStone(board, col, row, existingStone);
        Board newBoard=board;
        // Spróbuj umieścić kamień na tym samym miejscu
        Stone newStone = Stone.WHITE;
        if(ruleChecker.ifCanPlace(board, col,row,newStone)) {
            newBoard = ruleChecker.placeStone(board, col, row, newStone);
        }
        // Sprawdź, czy kamień nie został zmieniony
        assertEquals(existingStone, newBoard.getStones().get(col).get(row));
    }
}
