package com.TP;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Goban extends Pane {
    private final int size;
    private final double cellSize;
    public ArrayList<ArrayList<Integer>> board;
    public Goban(int size, double cellSize) {
        this.size = size;
        this.cellSize = cellSize;
        this.board = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                row.add(0);
            }
            board.add(row);
        }
        // to trzeba gdzies przeniesc
        initBoard();
        //i to tez
        setOnMouseClicked((MouseEvent e) -> {
            int row = (int) Math.floor(e.getX() / cellSize) + 1;
            int col = (int) Math.floor(e.getY() / cellSize) + 1;
            if(board.get(col-1).get(row-1)==0){
                addStone(row, col, Color.BLACK);
                board.get(col-1).set(row-1, 1);
                getStoneX(row);
                getStoneY(col);
                //sprawdzenie czy klik dobrze zapisuje dane do tabeli
                /*for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                     System.out.print(board.get(i).get(j) + " ");
                 }
                    System.out.println();
                   }*/
            }
        });
    }

    // sluzy do wyswietlania nowej planszy (razem z kamieniami zapisanymi w tablicy)
    public void createGame(Goban goban){
        Stage primaryStage = new Stage();
        StackPane root = new StackPane();
        root.getChildren().add(goban);

        Scene scene = new Scene(root, size * cellSize, size * cellSize);

        primaryStage.setTitle("Go Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        goban.updateGoban();
    }

    /*w sumie to nie wiem czy bedzie potrzebne bo
    zapisujemy wartosci kamieni do tabeli i pozniej
    za pomoca createGame tworzymy plansze z kamieniami
     */
    public double getStoneX(int row){

        return (row - 1) * cellSize + cellSize / 2;
    }

    public double getStoneY(int col){
        return  (col - 1) * cellSize + cellSize / 2;
    }
    public ArrayList<ArrayList<Integer>> getBoard() {
        return board;
    }
    private void initBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Rectangle cell = new Rectangle(i * cellSize, j * cellSize, cellSize, cellSize);
                cell.setFill(Color.BLACK);

                getChildren().add(cell);
                if (i >= 0 && i < size && j >= 0 && j < size) {
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



    //uzywane w createGame
    private void updateGoban() {
        getChildren().clear();
        initBoard();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board.get(i).get(j) == 1) {
                    addStone(i+1, j+1, Color.BLACK);
                } else if (board.get(i).get(j) == 2) {
                    addStone(i+1, j+1, Color.WHITE);
                }
            }
        }
    }

    //dodawanie kamieni w gui i do updateGoban
    private void addStone(int row, int col, Color color) {
        Stone stone = new Stone(cellSize);
        stone.setRadius(cellSize / 4);
        stone.setFill(color);

        // Find the center of the black rectangle
        double cellX = (row - 1) * cellSize + cellSize / 2;
        double cellY = (col - 1) * cellSize + cellSize / 2;

        // Check if the mouse click occurred within the bounds of the board
        if (cellX >= 2 && cellX < size * cellSize && cellY >= 2 && cellY < size * cellSize) {
            // Adjust the stone's position to be at the center of the black rectangle
            stone.setCenterX(cellX);
            stone.setCenterY(cellY);
            getChildren().add(stone);
        }
    }
}