package com.restready.gui;

import com.restready.gui.cashier.OrderEntryController;
import com.restready.gui.cashier.ProfileSelectionController;
import com.restready.gui.cashier.TicketsOverviewController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ClientApplication extends Application {

    @Override
    public void start(Stage stage) {

        StackPane root = new StackPane();

        PageNavigator navigator = new PageNavigator(root);
        navigator.registerPageFXML(
                ProfileSelectionController.class,
                "/fxml/cashier/profile-selection.fxml");
        navigator.registerPageFXML(
                TicketsOverviewController.class,
                "/fxml/cashier/tickets-overview.fxml");
        navigator.registerPageFXML(
                OrderEntryController.class,
                "/fxml/cashier/order-entry.fxml");

        navigator.navigateTo(OrderEntryController.class);

        Scene mainScene = new Scene(root, 800, 600);
        stage.setTitle("Restaurant Ready!");
        stage.setScene(mainScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
