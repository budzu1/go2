package com.tp.goserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bot {
    private final int boardSize;
    private Stone botStone;
    private int col;
    private int row;


    public Bot(int boardSize,Stone botStone) {
        this.boardSize = boardSize;
        this.botStone = botStone;
    }

    public void makeRandomMove(Board board) {
        List<Integer> emptyPositions = new ArrayList<>();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board.getStones().get(i).get(j) == Stone.EMPTY) {
                    emptyPositions.add(i * boardSize + j);
                }
            }
        }

        Random random = new Random();
        int randomIndex = random.nextInt(emptyPositions.size());
        int position = emptyPositions.get(randomIndex);


         col = position / boardSize;
         row = position % boardSize;
    }
    public int getCol(){
        return col;
    }
    public int getRow(){
        return row;
    }
    public Stone getStone(){
        return botStone;
    }
}
