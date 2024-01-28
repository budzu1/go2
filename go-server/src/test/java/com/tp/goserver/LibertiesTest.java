package com.tp.goserver;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LibertiesTest {

    @Test
    public void testUpdateLiberties() {
        // Tworzymy planszę
        Board board = new Board(9); // Załóżmy planszę o rozmiarze 9x9

        Stone stoneColor = Stone.BLACK;
        board.getStones().get(5).set(5,stoneColor);

        // Tworzymy obiekt Liberties
        Liberties liberties;
        liberties = new Liberties(9);
        liberties.updateLiberties(board);
        // Sprawdzamy, czy liczba swobód kamienia została poprawnie zaktualizowana
        assertEquals(4, liberties.getLiberties(5,5));

        // Dodajemy kamienie wokół, aby zmniejszyć liczbę swobód
        board.getStones().get(5).set(6,Stone.WHITE);
        board.getStones().get(4).set(5,Stone.WHITE);
        board.getStones().get(5).set(4,Stone.WHITE);
        board.getStones().get(6).set(5,Stone.WHITE);
        board.getStones().get(6).set(6,Stone.WHITE);


        // Aktualizujemy swobody kamienia
        liberties.updateLiberties(board);

        // Sprawdzamy, czy liczba swobód kamienia została poprawnie zaktualizowana
        assertEquals(0, liberties.getLiberties(5,5));
        assertEquals(2, liberties.getLiberties(6,6));
        assertEquals(2, liberties.getLiberties(5,6));
        assertEquals(2, liberties.getLiberties(6,5));
    }
}
