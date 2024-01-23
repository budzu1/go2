package com.TP;

import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginScreen extends Parent {

    public LoginScreen(final Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        final TextField usernameField = new TextField();
        Button loginButton = new Button("Login");

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loginUser(usernameField.getText(), primaryStage);
            }
        });

        layout.getChildren().addAll(new Label("Username:"), usernameField, loginButton);

        getChildren().add(layout);
    }

    private void loginUser(String username, final Stage primaryStage) {
        NetworkUtil.sendPostRequest("/game/login", "username", username)
                .thenAccept(new Consumer<String>() {
                    @Override
                    public void accept(final String response) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                MainScreen mainScreen = new MainScreen(primaryStage);
                                Scene scene = new Scene(mainScreen, 300, 200);
                                primaryStage.setScene(scene);
                            }
                        });
                    }
                });
    }
}
