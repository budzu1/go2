package com.TP;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;

public class Goban extends Pane {
    private final int size;
    private final double cellSize;

    public Goban(int size, double cellSize) {
        this.size = size;
        this.cellSize = cellSize;

        initBoard();
        setOnMouseClicked((MouseEvent e) -> {
            int row = (int) Math.floor(e.getX() / cellSize)+1 ;
            int col = (int) Math.floor(e.getY() / cellSize)+1 ;
            addStone(row, col, Color.BLACK);
        });
    }

    private void initBoard() {
        for (int i = 1; i < size-1; i++) {
            for (int j = 1; j < size-1; j++) {
                Rectangle cell = new Rectangle(i * cellSize, j * cellSize, cellSize, cellSize);
                cell.setFill(Color.BLACK);

                getChildren().add(cell);
                if (i > 0 && i < size - 1 && j > 0 && j < size - 1) {
                    double graySquareSize = 13;
                    Rectangle graySquare1 = new Rectangle(i * cellSize, j * cellSize, graySquareSize, graySquareSize);
                    Rectangle graySquare2 = new Rectangle((i + 1) * cellSize - graySquareSize, j * cellSize, graySquareSize, graySquareSize);
                    Rectangle graySquare3 = new Rectangle(i * cellSize, (j + 1) * cellSize - graySquareSize, graySquareSize, graySquareSize);
                    Rectangle graySquare4 = new Rectangle((i + 1) * cellSize - graySquareSize, (j + 1) * cellSize - graySquareSize, graySquareSize, graySquareSize);
                    graySquare1.setFill(Color.GRAY);
                    graySquare1.setStroke(Color.GRAY);
                    graySquare2.setFill(Color.GRAY);
                    graySquare2.setStroke(Color.GRAY);
                    graySquare3.setFill(Color.GRAY);
                    graySquare3.setStroke(Color.GRAY);
                    graySquare4.setFill(Color.GRAY);
                    graySquare4.setStroke(Color.GRAY);
                    getChildren().addAll(graySquare1, graySquare2, graySquare3, graySquare4);
                }
            }
        }
    }
    public void addStone(int row, int col, Color color) {
        Stone stone = new Stone(cellSize);
        stone.setRadius(cellSize / 4);
        stone.setFill(color);

        double centerX = (row - 1 + 0.5) * cellSize;
        double centerY = (col - 1 + 0.5) * cellSize;

        // Find the center of the black rectangle
        double cellX = (row - 1) * cellSize + cellSize / 2;
        double cellY = (col - 1) * cellSize + cellSize / 2;

        // Adjust the stone's position to be at the center of the black rectangle
        stone.setCenterX(cellX);
        stone.setCenterY(cellY);
        getChildren().add(stone);
    }

    private double snapToCell(double coord) {
        return Math.round(coord / cellSize) * cellSize;
    }

}