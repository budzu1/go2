package com.tp.goserver;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class StringMovesTest {

    @Test
    void testAppendMoveToString() {
        String initialString = "1,2;3,4;";
        String result = StringMoves.appendMoveToString(initialString, 5, 6);
        assertEquals("1,2;3,4;5,6;", result);

        // Test with empty string
        result = StringMoves.appendMoveToString("", 7, 8);
        assertEquals("7,8;", result);

        // Test with null
        result = StringMoves.appendMoveToString(null, 9, 10);
        assertEquals("9,10;", result);
    }

    @Test
    void testStringToMoveList() {
        // Test with valid string
        String movesString = "1,2;3,4;5,6;";
        ArrayList<Move> validMovesList = StringMoves.stringToMoveList(movesString);

        assertNotNull(validMovesList);
        assertEquals(3, validMovesList.size());
        assertAll("moves",
                () -> assertEquals(1, validMovesList.get(0).getX()),
                () -> assertEquals(2, validMovesList.get(0).getY()),
                () -> assertEquals(3, validMovesList.get(1).getX()),
                () -> assertEquals(4, validMovesList.get(1).getY()),
                () -> assertEquals(5, validMovesList.get(2).getX()),
                () -> assertEquals(6, validMovesList.get(2).getY()));

        // Test with empty string
        ArrayList<Move> emptyMovesList = StringMoves.stringToMoveList("");
        assertTrue(emptyMovesList.isEmpty());

        // Test with null
        ArrayList<Move> nullMovesList = StringMoves.stringToMoveList(null);
        assertTrue(nullMovesList.isEmpty());
    }

}
