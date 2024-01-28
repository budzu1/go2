package com.tp.goserver;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RuleCheckerTest2 {

    @Test
    public void testPlaceStoneAndRemoveStones() {
        // Tworzymy planszę
        Board board = new Board(9);

        // Tworzymy obiekt RuleChecker
        RuleChecker ruleChecker = new RuleChecker(9);



        board = ruleChecker.placeStone(board, 3, 3, Stone.BLACK);
        board = ruleChecker.placeStone(board, 3, 4, Stone.BLACK);
        board = ruleChecker.placeStone(board, 3, 5, Stone.BLACK);

        // Dodajemy kamienie wokół, aby zmniejszyć liczbę swobód i sprawdzamy, czy kamienie bez oddechów zostały usunięte
        board = ruleChecker.placeStone(board, 2, 3, Stone.WHITE);
        board = ruleChecker.placeStone(board, 2, 4, Stone.WHITE);
        board = ruleChecker.placeStone(board, 2, 5, Stone.WHITE);
        board = ruleChecker.placeStone(board, 3, 2, Stone.WHITE);
        board = ruleChecker.placeStone(board, 3, 6, Stone.WHITE);
        board = ruleChecker.placeStone(board, 4, 3, Stone.WHITE);
        board = ruleChecker.placeStone(board, 4, 4, Stone.WHITE);
        board = ruleChecker.placeStone(board, 4, 5, Stone.WHITE);


        // Sprawdzamy, czy kamienie z oddechami pozostały na planszy
        assertEquals(Stone.EMPTY, board.getStones().get(3).get(3));
        assertEquals(Stone.EMPTY, board.getStones().get(3).get(4));
        assertEquals(Stone.EMPTY, board.getStones().get(3).get(5));
    }
}
