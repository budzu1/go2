package com.TP;

import java.util.concurrent.CompletableFuture;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ReplayBoard extends Pane {
    private final int size;
    private final double cellSize;
    private ReplayMatrix rm;
    private String id;

    public ReplayBoard(int size, double cellSize, String id) {
        this.size = size;
        this.cellSize = cellSize;
        this.id = id;
    }

    public void createReplayWindow() {
        Stage replayStage = new Stage();
        VBox root = new VBox(10); // Vertical box with spacing 10

        // Initialize buttons and set their actions
        Button replayStartButton = new Button("Replay Start");
        replayStartButton.setOnAction(event -> {
            sendReplayStartRequest();

        });

        Button getNextButton = new Button("Get Next");
        getNextButton.setOnAction(event -> sendGetNextRequest());

        Button getPrevButton = new Button("Get Prev");
        getPrevButton.setOnAction(event -> sendGetPrevRequest());

        Button removeReplayButton = new Button("Remove Replay");
        removeReplayButton.setOnAction(event -> sendRemoveReplayRequest());

        // Add buttons to VBox
        root.getChildren().addAll(replayStartButton, getNextButton, getPrevButton, removeReplayButton);

        // Set scene and stage
        Scene scene = new Scene(root, 300, 200);
        replayStage.setScene(scene);
        replayStage.setTitle("Replay Board");
        replayStage.show();
    }

    private void sendReplayStartRequest() {
        CompletableFuture<String> responseFuture = NetworkUtil.sendPostRequest("/game/replayStart", "gameId", id);

        responseFuture.thenAccept(response -> {
            try {
                int size = Integer.parseInt(response);
                rm = new ReplayMatrix(size, 30.0, 1);
            } catch (NumberFormatException e) {
                System.err.println("Error parsing game ID: " + response);
            }
        }).exceptionally(ex -> {
            System.err.println("An error occurred: " + ex.getMessage());
            return null;
        });
    }

    private void sendGetNextRequest() {
        // Implement REST API call to /getNext
    }

    private void sendGetPrevRequest() {
        // Implement REST API call to /getPrev
    }

    private void sendRemoveReplayRequest() {
        // Implement REST API call to /removeReplay
    }
}
