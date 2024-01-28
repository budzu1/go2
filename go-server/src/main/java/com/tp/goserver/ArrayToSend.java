package com.tp.goserver;

import java.util.ArrayList;

public class ArrayToSend {

    ArrayList<ArrayList<Integer>> toSend;

    public ArrayToSend(ArrayList<ArrayList<Integer>> toSend) {
        this.toSend = toSend;
    }

    public ArrayList<ArrayList<Integer>> getToSend() {
        return toSend;
    }
}
