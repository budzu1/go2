package com.TP;

import javafx.scene.shape.Circle;
public class Stone extends Circle {
    private final double cellSize;

    public Stone(double cellSize) {
        this.cellSize = cellSize;
    }

    public void relocate(double x, double y) {
        super.relocate(snapToCell(x), snapToCell(y));
    }

    private double snapToCell(double coord) {
        return Math.round(coord / cellSize) * cellSize;
    }
}