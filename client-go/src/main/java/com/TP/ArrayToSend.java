package com.TP;

import java.util.ArrayList;

public class ArrayToSend {

    ArrayList<ArrayList<Integer>> toSend;

    public ArrayToSend() {
    }

    public ArrayToSend(ArrayList<ArrayList<Integer>> toSend) {
        this.toSend = toSend;
    }

    public ArrayList<ArrayList<Integer>> getToSend() {
        return toSend;
    }

    public ArrayList<ArrayList<Integer>> getEmpty(int n) {
        ArrayList<ArrayList<Integer>> fullTemp = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> halfTemp = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                halfTemp.add(0);
            }
            fullTemp.add(halfTemp);
        }

        return fullTemp;
    }
}
