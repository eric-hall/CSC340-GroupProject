package com.restready.gui;

import com.restready.common.CommonModuleAccessTest;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RestaurantReadyClientController {

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        CommonModuleAccessTest.echo("RestaurantReadyClient: button pressed!");
        welcomeText.setText("JavaFX is working!");
    }
}
