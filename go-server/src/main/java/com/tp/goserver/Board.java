package com.tp.goserver;

import java.util.ArrayList;

public class Board {

    private int n;

    private ArrayList<ArrayList<Stone>> stones = new ArrayList<>();

    private ArrayList<ArrayList<Stone>> last = new ArrayList<>();

    public Board(int n) {
        addStones(n);
        this.n = n;
    }
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (ArrayList<Stone> row : stones) {
            for (Stone stone : row) {
                result.append(stone).append(" ");
            }
            result.append("\n");
        }
        return result.toString();
    }


    private void addStones(int n) {

        for (int i = 0; i < n; i++) {

            ArrayList<Stone> tempArray = new ArrayList<>();

            for (int j = 0; j < n; j++) {

                tempArray.add(Stone.EMPTY);
            }

            stones.add(tempArray);
        }
    }

    public void saveLastMove(ArrayList<ArrayList<Stone>> last) {
        this.last = last;
    }

    public ArrayList<ArrayList<Stone>> getStones() {
        return this.stones;
    }

    public ArrayList<ArrayList<Integer>> prepareToSend() {

        ArrayList<ArrayList<Integer>> toSend = new ArrayList<>();
        for (int i = 0; i < n; i++) {

            ArrayList<Integer> tempArray = new ArrayList<>();

            for (int j = 0; j < n; j++) {

                Stone stn = stones.get(i).get(j);
                int whatStone;
                if (stn == Stone.EMPTY) {
                    whatStone = 0;
                } else if (stn == Stone.WHITE) {
                    whatStone = 2;
                } else if (stn == Stone.BLACK) {
                    whatStone = 1;
                } else {
                    whatStone = 0;
                }

                tempArray.add(whatStone);
            }

            toSend.add(tempArray);
        }

        return toSend;
    }

}
