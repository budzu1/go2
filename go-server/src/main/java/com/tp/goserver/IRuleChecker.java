package com.tp.goserver;

public interface IRuleChecker {

    public boolean ifCanPlace(Board board, int i, int j);

    public Board placeStone(Board board, int i, int j,Stone stone);
}
