package com.restready.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class RestaurantReadyClient extends Application {

    private static final String HELLO_VIEW_PATH = "/fxml/hello-view.fxml";

    @Override
    public void start(Stage stage) throws IOException {
        URL url = RestaurantReadyClient.class.getResource(HELLO_VIEW_PATH);
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
