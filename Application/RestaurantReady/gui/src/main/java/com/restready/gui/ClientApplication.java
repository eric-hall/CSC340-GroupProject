package com.restready.gui;

import com.restready.gui.cashier.OrderEntryController;
import com.restready.gui.cashier.ProfileSelectionController;
import com.restready.gui.cashier.TicketsOverviewController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ClientApplication extends Application {

    @Override
    public void start(Stage stage) {

        PageNavigator pages = new PageNavigator(); // Main Scene

        pages.registerPageFXML(
                ProfileSelectionController.class,
                "/fxml/cashier/profile-selection.fxml");
        pages.registerPageFXML(
                TicketsOverviewController.class,
                "/fxml/cashier/tickets-overview.fxml");
        pages.registerPageFXML(
                OrderEntryController.class,
                "/fxml/cashier/order-entry.fxml");

        // Open the first page
        pages.navigateTo(OrderEntryController.class);

        // Initialize the stage
        stage.setTitle("Restaurant Ready!");
        stage.setScene(pages.getMainScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
