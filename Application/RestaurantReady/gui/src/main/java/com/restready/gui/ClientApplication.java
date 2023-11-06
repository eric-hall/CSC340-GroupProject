package com.restready.gui;

import com.restready.gui.cashier.OrderEntryController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        // TODO: Page navigation handler or abstraction (idea still fuzzy)

//        TicketsOverviewController page = Controller.loadFXML(
//                "/fxml/cashier/tickets-overview.fxml",
//                TicketsOverviewController.class);
        OrderEntryController page = Controller.loadFXML(
                "/fxml/cashier/order-entry.fxml",
                OrderEntryController.class);

        Scene mainScene = new Scene(page.getRoot());
        stage.setTitle("Restaurant Ready!");
        stage.setScene(mainScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
