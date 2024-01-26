package com.TP;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


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
            Goban goban = new Goban(boardSize, cellSize);
            goban.createGame(goban);
            //zamina wartosci tabeli zeby sprawdzic czy wczytuje odpowiednio kamienie na plansze
            //goban.getBoard().get(0).set(0, 1);
            //goban.getBoard().get(boardSize-1).set(boardSize-1 , 2);
            //goban.updateGoban();
        });

    }




    private void joinGame() {
        // Implement logic to join a game via server
    }

    private void rollDice() {
        // Implement logic to roll dice via server
    }

}
