package com.TP;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.geometry.Insets;

public class MainScreen extends Parent {
    private Long gameId;

    private CountDownLatch joinGameLatch = new CountDownLatch(1);

    public MainScreen(final Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        Button createGameButton = new Button("Create Game");
        Button joinGameButton = new Button("Join Game");
        Button replayButton = new Button("Replay");

        createGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createGame(new Stage());
            }
        });

        joinGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                joinGame(new Stage());
            }
        });


        replayButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                replay(new Stage());
            }
        });

        layout.getChildren().addAll(createGameButton, joinGameButton,replayButton);

        getChildren().add(layout);
    }

    private void replay(Stage primaryStage) {
        Stage replayStage = new Stage();

        VBox layout = new VBox(10);

        TextField inputField = new TextField();
        inputField.setPromptText("Enter something here");

        Button confirmButton = new Button("Confirm");

        confirmButton.setOnAction(event -> {
            String input = inputField.getText();
            replayStage.close();
            ReplayBoard replayBoard = new ReplayBoard(19, 20, input); // Example size and cellSize
            replayBoard.createReplayWindow();
        });

        layout.getChildren().addAll(inputField, confirmButton);

        Scene scene = new Scene(layout, 300, 120);
        replayStage.setScene(scene);

        replayStage.setTitle("Replay Input");
        replayStage.initOwner(primaryStage);
        replayStage.showAndWait();
    }

    private void createGame(Stage primaryStage) {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Small (9x9)", "Medium (13x13)", "Large (19x19)");
        dialog.setTitle("Choose Board Size");
        dialog.setHeaderText("Select the board size:");
        dialog.setContentText("Board Size:");

        // Pobranie wyboru użytkownika
        dialog.showAndWait().ifPresent(result -> {
            int boardSize;
            double cellSize = 30;

            switch (result) {
                case "Small (9x9)":
                    boardSize = 9;
                    break;
                case "Medium (13x13)":
                    boardSize = 13;
                    break;
                case "Large (19x19)":
                default:
                    boardSize = 19;
                    break;
            }


                    // Tryb gry z graczem
                    GameSession.getInstance().setSize(boardSize);
                    sendCreateGame(GameSession.getInstance().getUserId(), GameSession.getInstance().getSize());
                    Goban goban = new Goban(boardSize, cellSize, 1);
                    try {
                        URI uri = new URI("ws://localhost:8080/client");
                        SimpleWebSocketClient swc = new SimpleWebSocketClient(uri, goban);
                        goban.createGame(goban);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void joinGame(Stage primaryStage) {

        primaryStage.setTitle("Join game");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Label label = new Label("Write game ID:");

        TextField textField = new TextField();

        javafx.scene.control.Button confirmButton = new javafx.scene.control.Button("Confirm");
        confirmButton.setOnAction(e -> {
            try {
                gameId = Long.parseLong(textField.getText());
                primaryStage.close();
                GameSession.getInstance().setGameId(gameId);
                sendJoinGame(GameSession.getInstance().getUserId(), GameSession.getInstance().getGameId());
            } catch (NumberFormatException ex) {
            }
        });
        layout.getChildren().addAll(label, textField, confirmButton);

        Scene scene = new Scene(layout, 250, 150);
        primaryStage.setScene(scene);

        primaryStage.initModality(Modality.APPLICATION_MODAL);

        primaryStage.showAndWait();

        try {
            // Wait for sendJoinGame to complete
            joinGameLatch.await();

            // Now, you can create the Goban instance
            Goban goban = new Goban(GameSession.getInstance().getSize(), 30, 2);
            goban.createGame(goban);
        } catch (InterruptedException e) {
            // Handle interruption if necessary
        }
    }

    private void sendCreateGame(String creator, int size) {
        CompletableFuture<String> responseFuture = NetworkUtil.sendDoublePostRequest("/game/create", "creator", creator,
                "size", Integer.toString(size));

        responseFuture.thenAccept(response -> {
            try {
                long gameId = Long.parseLong(response);
                System.out.println("Game ID: " + gameId);
                GameSession.getInstance().setGameId(gameId);
            } catch (NumberFormatException e) {
                System.err.println("Error parsing game ID: " + response);
            }
        }).exceptionally(ex -> {
            System.err.println("An error occurred: " + ex.getMessage());
            return null;
        });
    }

    private void sendJoinGame(String login, Long gameId) {
        CompletableFuture<String> responseFuture = NetworkUtil.sendDoublePostRequest("/game/join", "gameId",
                Long.toString(gameId), "opponent", login);

        responseFuture.thenAccept(response -> {
            try {
                int size = Integer.parseInt(response);
                System.out.println("Size: " + size);
                GameSession.getInstance().setSize(size);
                System.out.println("Size: " + GameSession.getInstance().getSize());
                joinGameLatch.countDown();
            } catch (NumberFormatException e) {
                System.err.println("Error parsing game ID: " + response);
            }
        }).exceptionally(ex -> {
            System.err.println("An error occurred: " + ex.getMessage());
            return null;
        });
    }
}
