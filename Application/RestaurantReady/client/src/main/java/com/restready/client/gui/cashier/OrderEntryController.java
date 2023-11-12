package com.restready.client.gui.cashier;

import com.restready.common.util.Log;
import com.restready.client.gui.PageController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;

public class OrderEntryController extends PageController {

    // TODO: T e m p o r a r y   E x a m p l e . . .
    private static final String[] EXAMPLE_BUTTON_CONTENT = {
            "Burger", "Pizza", "Mom's Spaghetti",
            "Dad's Spaghetti", "Chicken Plate", "Salad",
            "Ravioli", "Beef Special", "Sam's Hot-dog",
            "Nachos", "Daily Soup", "Arancini"
    };
    private static final int ENTRIES_PER_ROW = 5;
    private static final int ROW_HEIGHT = 60; // Pixels

    @FXML
    private Parent toolbar;
    @FXML
    private GridPane gridPane;

    @FXML
    public void initialize() {

        // Set column constraints (per column)
        ColumnConstraints widthLimiter = new ColumnConstraints();
        widthLimiter.setPercentWidth(100.0d / ENTRIES_PER_ROW);
        for (int i = 0; i < ENTRIES_PER_ROW; ++i) {
            gridPane.getColumnConstraints().add(widthLimiter);
        }

        OrderEntryController controller = this;
        for (int i = 0; i < EXAMPLE_BUTTON_CONTENT.length; ++i) {
            int x = i % ENTRIES_PER_ROW;
            int y = i / ENTRIES_PER_ROW;
            String buttonPressedText = EXAMPLE_BUTTON_CONTENT[i] + " Pressed!";
            addProductItem(EXAMPLE_BUTTON_CONTENT[i] , e -> Log.info(controller, buttonPressedText), x, y);
        }
    }

    @Override
    public void onPageShow() {
        Log.debug(this, "onPageShow");
    }

    @Override
    public void onPageHide() {
        Log.debug(this, "onPageHide");
    }

    private void addProductItem(String buttonText, EventHandler<ActionEvent> action, int x, int y) {
        Button button = new Button(buttonText);
        button.setTextAlignment(TextAlignment.CENTER);
        button.setWrapText(true);
        button.setPrefSize(Double.MAX_VALUE, ROW_HEIGHT);
        button.setOnAction(action);
        gridPane.add(button, x, y);
    }
}
