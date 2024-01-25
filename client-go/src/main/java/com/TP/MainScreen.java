package com.TP;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class MainScreen extends Parent {
    private static final int BUTTON_SIZE = 50;
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
                joinGame();
            }
        });

        rollDiceButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                rollDice();
            }
        });

        layout.getChildren().addAll(createGameButton, joinGameButton, rollDiceButton);

        getChildren().add(layout);
    }

    private void createGame(Stage primaryStage) {
        int boardSize = 19;
        double cellSize = 30;
        Goban goban = new Goban(boardSize, cellSize);
        StackPane root = new StackPane();
        root.getChildren().add(goban);

        Scene scene = new Scene(root, boardSize * cellSize, boardSize * cellSize);

        primaryStage.setTitle("Go Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }




    private void joinGame() {
        // Implement logic to join a game via server
    }

    private void rollDice() {
        // Implement logic to roll dice via server
    }

}
