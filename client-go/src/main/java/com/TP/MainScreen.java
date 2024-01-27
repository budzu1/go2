package com.TP;

import java.util.concurrent.CompletableFuture;

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
    private static final int BUTTON_SIZE = 50;
    private Long gameId;

    public MainScreen(final Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        Button createGameButton = new Button("Create Game");
        Button joinGameButton = new Button("Join Game");
        Button rollDiceButton = new Button("Roll Dice");

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

        rollDiceButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        layout.getChildren().addAll(createGameButton, joinGameButton, rollDiceButton);

        getChildren().add(layout);
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

            // sygnał do serwera
            sendCreateGame(GameSession.getInstance().getUserId());

            Goban goban = new Goban(boardSize, cellSize);
            goban.createGame(goban);
        });

    }

    private void joinGame() {
        // Implement logic to join a game via server
    }

    private void joinGame(Stage primaryStage) {
        primaryStage.setTitle("Twoja Klasa");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        Label label = new Label("Podaj ID gry:");

        TextField textField = new TextField();

        javafx.scene.control.Button confirmButton = new javafx.scene.control.Button("Potwierdź");
        confirmButton.setOnAction(e -> {
            try {
                gameId = Long.parseLong(textField.getText());
                primaryStage.close();
            } catch (NumberFormatException ex) {
            }
        });

        layout.getChildren().addAll(label, textField, confirmButton);

        Scene scene = new Scene(layout, 250, 150);
        primaryStage.setScene(scene);

        primaryStage.initModality(Modality.APPLICATION_MODAL);

        primaryStage.showAndWait();
    }

    private void rollDice() {
        // Implement logic to roll dice via server
    }

    private static void sendCreateGame(String creator) {
        CompletableFuture<String> responseFuture = NetworkUtil.sendPostRequest("/game/create", "creator", creator);

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

    public Long getGameId(Long gameId) {
        return gameId;
    }
}
