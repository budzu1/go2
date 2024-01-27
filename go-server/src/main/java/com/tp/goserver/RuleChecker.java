package com.tp.goserver;

import java.util.ArrayList;

public final class RuleChecker implements IRuleChecker {

    public boolean ifCanPlace(Board board, int i, int j) {

        return true;
    }

    public Board placeStone(Board board, int i, int j) {
        return new Board(0);
    }
}
