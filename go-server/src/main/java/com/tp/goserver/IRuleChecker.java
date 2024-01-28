package com.tp.goserver;

public interface IRuleChecker {

    public boolean ifCanPlace(Board board, int i, int j, Stone stone);

    public Board placeStone(Board board, int i, int j, Stone stone);
}
