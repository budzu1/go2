package com.TP;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Initialize your primary scene here, for example, the login screen
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
