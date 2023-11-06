package com.restready.gui.cashier;

import com.restready.gui.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DefaultToolbarController extends Controller {

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
