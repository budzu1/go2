package com.tp.goserver;

import java.util.ArrayList;

public class Board {

    private ArrayList<ArrayList<Stone>> stones = new ArrayList<>();

    public Board(int n) {
        addStones(n);
    }

    private void addStones(int n) {

        for (int i = 0; i < n; i++) {

            ArrayList<Stone> tempArray = new ArrayList<>();

            for (int j = 0; j < n; j++) {

                tempArray.add(new Stone());
            }

            stones.add(tempArray);
        }
    }
}
