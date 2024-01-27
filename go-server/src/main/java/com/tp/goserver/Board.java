package com.tp.goserver;

import java.util.ArrayList;

public class Board {

    private int n;

    private ArrayList<ArrayList<Stone>> stones = new ArrayList<>();

    private ArrayList<ArrayList<Stone>> last = new ArrayList<>();

    public Board(int n) {
        addStones(n);
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
}
