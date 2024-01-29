package com.tp.goserver;

public class ReplayBlack implements ReplayState {

    @Override
    public void addMove(Replay replay, int row, int col, int boardCount) {
        Board tempBoard = replay.getBoard(boardCount);
        if (!replay.getRules().ifCanPlace(tempBoard, col, row, Stone.BLACK)) {
            return;
        }

        replay.addBoard(replay.getRules().placeStone(tempBoard, col, row, Stone.BLACK));
        replay.setState(new ReplayWhite());

    }
}
