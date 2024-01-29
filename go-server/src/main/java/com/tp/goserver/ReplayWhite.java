package com.tp.goserver;

public class ReplayWhite implements ReplayState {

    @Override
    public void addMove(Replay replay, int row, int col, int boardCount) {
        Board tempBoard = replay.getBoard(boardCount);
        if (!replay.getRules().ifCanPlace(tempBoard, col, row, Stone.WHITE)) {
            return;
        }

        replay.addBoard(replay.getRules().placeStone(tempBoard, col, row, Stone.WHITE));
        replay.setState(new ReplayBlack());

    }
}
