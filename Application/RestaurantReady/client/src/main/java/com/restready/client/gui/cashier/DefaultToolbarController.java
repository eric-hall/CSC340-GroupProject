package com.restready.client.gui.cashier;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DefaultToolbarController {

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
