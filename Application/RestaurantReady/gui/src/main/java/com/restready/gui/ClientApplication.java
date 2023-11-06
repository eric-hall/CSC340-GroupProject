package com.restready.gui;

import com.restready.gui.cashier.OrderEntryController;
import com.restready.gui.cashier.TicketsOverviewController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        StackPane root = new StackPane();

        PageNavigator navigator = new PageNavigator(root);
        navigator.registerFXML(
                OrderEntryController.class,
                "/fxml/cashier/order-entry.fxml");
        navigator.registerFXML(
                TicketsOverviewController.class,
                "/fxml/cashier/tickets-overview.fxml");

        navigator.navigateTo(TicketsOverviewController.class);

        Scene mainScene = new Scene(root, 800, 600);
        stage.setTitle("Restaurant Ready!");
        stage.setScene(mainScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
