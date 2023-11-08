package com.restready.gui.cashier;

import com.restready.gui.EmbeddedController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DefaultToolbarController extends EmbeddedController {

    @FXML
    private Button backButton;
    @FXML
    private Button homeButton;
    @FXML
    private Label mainLabel;

    public void setToolbarLabelText(String text) {
        mainLabel.setText(text);
    }

    public void setOnBackButtonPressed(EventHandler<ActionEvent> action) {
        backButton.setOnAction(action);
    }

    public void setOnHomeButtonPressed(EventHandler<ActionEvent> action) {
        homeButton.setOnAction(action);
    }
}
