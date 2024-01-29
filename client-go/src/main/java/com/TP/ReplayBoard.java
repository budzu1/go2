package com.TP;

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
    // Other necessary fields...

    public ReplayBoard(int size, double cellSize) {
        this.size = size;
        this.cellSize = cellSize;
    }

    public void createReplayWindow() {
        Stage replayStage = new Stage();
        VBox root = new VBox(10); // Vertical box with spacing 10

        // Initialize buttons and set their actions
        Button replayStartButton = new Button("Replay Start");
        replayStartButton.setOnAction(event -> sendReplayStartRequest());

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
        // Implement REST API call to /replayStart
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

    // Other methods (e.g., to update the board)...
}
