package com.TP;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainScreen extends Parent {

    public MainScreen(final Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        Button createGameButton = new Button("Create Game");
        Button joinGameButton = new Button("Join Game");
        Button rollDiceButton = new Button("Roll Dice");

        createGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createGame();
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

    private void createGame() {
        // Implement logic to create a game via server
    }

    private void joinGame() {
        // Implement logic to join a game via server
    }

    private void rollDice() {
        // Implement logic to roll dice via server
    }
}
