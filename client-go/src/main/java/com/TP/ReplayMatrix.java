package com.TP;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ReplayMatrix extends Pane {

    private final int size;
    private final double cellSize;
    private final int color;
    public ArrayList<ArrayList<Integer>> board;
    private List<Stone> stones = new ArrayList<>();

    public ReplayMatrix(int size, double cellSize, int color) {
        this.size = size;
        this.cellSize = cellSize;
        this.board = new ArrayList<>();
        this.color = color;

        for (int i = 0; i < size; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                row.add(0);
            }
            board.add(row);
        }
        initBoard();
    }

    public void setBoard(ArrayList<ArrayList<Integer>> board) {
        this.board = board;
    }

    private void initBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Rectangle cell = new Rectangle(i * cellSize, j * cellSize, cellSize, cellSize);
                cell.setFill(Color.BLACK);

                getChildren().add(cell);

                double graySquareSize = 13;
                Rectangle graySquare1 = new Rectangle(i * cellSize, j * cellSize, graySquareSize, graySquareSize);
                Rectangle graySquare2 = new Rectangle((i + 1) * cellSize - graySquareSize, j * cellSize, graySquareSize,
                        graySquareSize);
                Rectangle graySquare3 = new Rectangle(i * cellSize, (j + 1) * cellSize - graySquareSize, graySquareSize,
                        graySquareSize);
                Rectangle graySquare4 = new Rectangle((i + 1) * cellSize - graySquareSize,
                        (j + 1) * cellSize - graySquareSize, graySquareSize, graySquareSize);
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

    public void createGame(Goban goban) {
        Stage primaryStage = new Stage();
        StackPane root = new StackPane();
        root.getChildren().add(goban);

        Scene scene = new Scene(root, size * cellSize, size * cellSize);

        primaryStage.setTitle("Go Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void updateGoban() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board.get(i).get(j) == 1) {
                    addStone(j + 1, i + 1, Color.BLACK);
                } else if (board.get(i).get(j) == 2) {
                    addStone(j + 1, i + 1, Color.WHITE);
                } else {
                    removeStone(j + 1, i + 1);
                }
            }
        }
    }

    private void addStone(int row, int col, Color color) {
        Stone stone = new Stone(cellSize, row, col);
        stone.setRadius(cellSize / 4);
        stone.setFill(color);

        double cellX = (row - 1) * cellSize + cellSize / 2;
        double cellY = (col - 1) * cellSize + cellSize / 2;

        if (cellX >= 2 && cellX < size * cellSize && cellY >= 2 && cellY < size * cellSize) {
            stone.setCenterX(cellX);
            stone.setCenterY(cellY);
            getChildren().add(stone);
            stones.add(stone);
        }
    }

    private void removeStone(int row, int col) {
        // Utwórz iterator do bezpiecznego usuwania elementów podczas iteracji
        Iterator<Stone> iterator = stones.iterator();
        while (iterator.hasNext()) {
            Stone stone = iterator.next();
            if (stone.getRow() == row && stone.getCol() == col) {
                getChildren().remove(stone);
                iterator.remove(); // Użyj iteratora do bezpiecznego usunięcia elementu
            }
        }
    }
}
