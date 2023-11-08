package com.restready.gui.cashier;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

// TODO: Don't extend Controller -- extend some kind of "EmbeddedController" instead?
//  - EmbeddedController class does not exist yet.
//  - The Controller could provide a map to its EmbeddedControllers.
//  - EmbeddedControllers will never be registered in PageNavigator.
public class DefaultToolbarController {

    @FXML
    private Button toolbarBackButton;
    @FXML
    private Button toolbarHomeButton;
    @FXML
    private Label toolbarLabel;

    public void setToolbarLabelText(String text) {
        toolbarLabel.setText(text);
    }

    public void setOnToolbarBackButtonPressed(EventHandler<ActionEvent> action) {
        toolbarBackButton.setOnAction(action);
    }

    public void setOnToolbarHomeButtonPressed(EventHandler<ActionEvent> action) {
        toolbarHomeButton.setOnAction(action);
    }
}
