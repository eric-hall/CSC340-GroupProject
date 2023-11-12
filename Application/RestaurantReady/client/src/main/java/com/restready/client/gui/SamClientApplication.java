package com.restready.client.gui;

import com.restready.client.gui.admin.ProductCatalogsController;
import com.restready.common.util.Log;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

// TODO: Temporary for development purposes (page navigation not fully implemented)
public class SamClientApplication extends Application {

    // NOTE: Use "Log" class for static printing instead of System.out

    @Override
    public void start(Stage stage) throws IOException {
        ProductCatalogsController page = PageController.loadFXML(
                null, "/fxml/admin/product-catalogs.fxml",
                ProductCatalogsController.class);
        Scene mainScene = new Scene(page.getRoot(), 800, 600);
        stage.setTitle("Restaurant Ready!");
        stage.setScene(mainScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(SamClientApplication.class);
    }
}
