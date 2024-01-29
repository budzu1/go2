package com.TP;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Goban extends Pane {
    private final int size;
    private final double cellSize;
    private final int color;
    public ArrayList<ArrayList<Integer>> board;
    private List<Stone> stones = new ArrayList<>();
    private int threadCheck = 0;

    Thread refresh = new Thread(() -> {
        while (threadCheck == 0) {
            try {
                Thread.sleep(100);
                sendRequest();
            } catch (Exception e) {

            }
        }
    });

    Thread winnerHunt = new Thread(() -> {
        while (threadCheck == 0) {
            try {
                Thread.sleep(2000);
                sendGetWinner();
            } catch (Exception e) {

            }
        }
    });

    public Goban(int size, double cellSize, int color) {
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
        setOnMouseClicked((MouseEvent e) -> {
            int row = (int) Math.floor(e.getX() / cellSize) + 1;
            int col = (int) Math.floor(e.getY() / cellSize) + 1;
            System.out.println(row);
            System.out.println(col);
            /*
             * if (board.get(col - 1).get(row - 1) == 0) {
             * board.get(col - 1).set(row - 1, color);
             * updateGoban();
             * getStoneX(row);
             * getStoneY(col);
             * }
             */

            // wysyłanie requesta na serwer
            sendMakeMove(row, col, GameSession.getInstance().getUserId(), GameSession.getInstance().getGameId());
        });
    }

    public void createGame(Goban goban) {
        Stage primaryStage = new Stage();
        StackPane root = new StackPane();
        root.getChildren().add(goban);

        Scene scene = new Scene(root, size * cellSize, size * cellSize);

        primaryStage.setTitle("Go Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        refresh.start();
        winnerHunt.start();
        createButtonWindow();
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

    // dodawanie kamieni w updateGoban
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

    private void sendMakeMove(int row, int col, String login,
            Long gameId) {
        CompletableFuture<String> responseFuture = NetworkUtil.sendQuadraPostRequest("/game/makeMove", "row",
                Integer.toString(row), "col", Integer.toString(col), "login", login,
                "gameId", Long.toString(gameId));

        responseFuture.thenAccept(response -> {
            try {
                int size = Integer.parseInt(response);
                System.out.println("Size: " + size);
                GameSession.getInstance().setSize(size);
            } catch (NumberFormatException e) {
                System.err.println("Error parsing game ID: " + response);
            }
        }).exceptionally(ex -> {
            System.err.println("An error occurred: " + ex.getMessage());
            return null;
        });
    }

    private void sendRequest() {
        CompletableFuture<String> responseFuture = NetworkUtil.sendDoublePostRequest("/game/refresh", "gameId",
                Long.toString(GameSession.getInstance().getGameId()), "login", GameSession.getInstance().getUserId());

        responseFuture.thenAccept(response -> {
            try {
                ObjectMapper om = new ObjectMapper();
                try {
                    ArrayToSend temp = om.readValue(response, ArrayToSend.class);
                    board = temp.getToSend();
                    Platform.runLater(() -> {
                        updateGoban();
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (NumberFormatException e) {
                System.err.println("Error");
            }
        }).exceptionally(ex -> {
            System.err.println("An error occurred: " + ex.getMessage());
            return null;
        });
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

    public void setBoard(ArrayList<ArrayList<Integer>> board) {
        this.board = board;
    }

    private ArrayList<Integer> getCol(int col) {
        return board.get(col - 1);
    }

    private ArrayList<Integer> getRow(int row) {
        return board.get(row - 1);
    }

    public void createButtonWindow() {
        Stage buttonStage = new Stage();

        // Create buttons
        Button passButton = new Button("Pass");
        Button giveUpButton = new Button("Give Up");
        Button continueButton = new Button("Continue");

        // Set actions for buttons
        passButton.setOnAction(event -> {
            sendPass();
        });

        giveUpButton.setOnAction(event -> {
            sendGiveUp();
        });

        continueButton.setOnAction(event -> {
            sendPContinue();
        });

        // HBox for holding buttons
        HBox buttonBox = new HBox(10); // 10 is spacing between buttons
        buttonBox.getChildren().addAll(passButton, giveUpButton, continueButton);

        // Set the HBox as the scene for the new stage
        Scene buttonScene = new Scene(buttonBox);
        buttonStage.setTitle("Game Options");
        buttonStage.setScene(buttonScene);
        buttonStage.show();
    }

    private void sendPass() {
        CompletableFuture<String> responseFuture = NetworkUtil.sendDoublePostRequest("/game/pass", "gameId",
                Long.toString(GameSession.getInstance().getGameId()), "login", GameSession.getInstance().getUserId());

        responseFuture.thenAccept(response -> {
            try {
                int size = Integer.parseInt(response);
                System.out.println("Size: " + size);
                GameSession.getInstance().setSize(size);
            } catch (NumberFormatException e) {
                System.err.println("Error parsing game ID: " + response);
            }
        }).exceptionally(ex -> {
            System.err.println("An error occurred: " + ex.getMessage());
            return null;
        });
    }

    private void sendGiveUp() {
        CompletableFuture<String> responseFuture = NetworkUtil.sendDoublePostRequest("/game/giveUp", "gameId",
                Long.toString(GameSession.getInstance().getGameId()), "login", GameSession.getInstance().getUserId());

        responseFuture.thenAccept(response -> {
            try {
                int size = Integer.parseInt(response);
                System.out.println("Size: " + size);
                GameSession.getInstance().setSize(size);
            } catch (NumberFormatException e) {
                System.err.println("Error parsing game ID: " + response);
            }
        }).exceptionally(ex -> {
            System.err.println("An error occurred: " + ex.getMessage());
            return null;
        });
    }

    private void sendPContinue() {
        CompletableFuture<String> responseFuture = NetworkUtil.sendDoublePostRequest("/game/pContinue", "gameId",
                Long.toString(GameSession.getInstance().getGameId()), "login", GameSession.getInstance().getUserId());

        responseFuture.thenAccept(response -> {
            try {
                int size = Integer.parseInt(response);
                System.out.println("Size: " + size);
                GameSession.getInstance().setSize(size);
            } catch (NumberFormatException e) {
                System.err.println("Error parsing game ID: " + response);
            }
        }).exceptionally(ex -> {
            System.err.println("An error occurred: " + ex.getMessage());
            return null;
        });
    }

    private void sendGetWinner() {
        CompletableFuture<String> responseFuture = NetworkUtil.sendPostRequest("/game/getWinner", "gameId",
                Long.toString(GameSession.getInstance().getGameId()));

        responseFuture.thenAccept(response -> {
            try {
                String winner = response;

                if (!winner.equals("")) {
                    threadCheck = 1;
                    Platform.runLater(() -> {
                        showMessageWindow("Winner is: " + winner);
                    });
                }

            } catch (NumberFormatException e) {
                System.err.println("Error parsing winner: " + response);
            }
        }).exceptionally(ex -> {
            System.err.println("An error occurred: " + ex.getMessage());
            return null;
        });
    }

    public void showMessageWindow(String message) {
        Stage messageStage = new Stage();

        messageStage.initModality(Modality.APPLICATION_MODAL);

        Label messageLabel = new Label(message);

        VBox layout = new VBox(10);
        layout.getChildren().add(messageLabel);

        Scene scene = new Scene(layout, 300, 100);
        messageStage.setScene(scene);

        messageStage.setTitle("Message");
        messageStage.showAndWait();
    }
}