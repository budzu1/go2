package com.TP;

import javafx.scene.shape.Circle;
public class Stone extends Circle {
    private final double cellSize;
    final private int row;
    final private int col;

    public Stone(double cellSize,int row, int col ) {
        this.cellSize=cellSize;
        this.row = row;
        this.col = col;
        setRadius(cellSize / 4);
    }

    public int getRow() {
        return row;
    }
    public int getCol(){
        return col;
    }


    public void relocate(double x, double y) {
        super.relocate(snapToCell(x), snapToCell(y));
    }

    private double snapToCell(double coord) {
        return Math.round(coord / cellSize) * cellSize;
    }

}