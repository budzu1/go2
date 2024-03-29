package com.TP;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        LoginScreen loginScreen = new LoginScreen(primaryStage);
        Scene scene = new Scene(loginScreen, 300, 200);

        primaryStage.setTitle("Go Game Client");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
