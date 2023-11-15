package com.restready.client.gui.cashier;

import com.restready.common.Product;
import com.restready.common.ProductCatalog;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;

import java.util.function.Consumer;

public class ProductCatalogBrowserController {

    private static final int PRODUCTS_PER_ROW = 6;
    private static final int ROW_HEIGHT = 60; // Pixels

    @FXML
    private GridPane productsGridPane;

    private Consumer<Product> productButtonPressedHandler;

    @FXML
    private void initialize() {

        // Set GridPane column constraints
        ColumnConstraints widthLimiter = new ColumnConstraints();
        widthLimiter.setPercentWidth(100.0d / PRODUCTS_PER_ROW);
        for (int i = 0; i < PRODUCTS_PER_ROW; ++i) {
            productsGridPane.getColumnConstraints().add(widthLimiter);
        }
    }

    public void setProductCatalog(ProductCatalog catalog) {

        // Initialize products GridPane.
        productsGridPane.getChildren().clear();

        int i = 0;
        for (Product product : catalog) {
            Button button = new Button(product.getName());
            button.setTextAlignment(TextAlignment.CENTER);
            button.setWrapText(true);
            button.setPrefSize(Double.MAX_VALUE, ROW_HEIGHT);
            button.setOnAction(e -> onProductButtonPressed(product));
            int x = i % PRODUCTS_PER_ROW;
            int y = i / PRODUCTS_PER_ROW;
            productsGridPane.add(button, x, y);
            i += 1;
        }
    }

    public void setProductButtonPressedHandler(Consumer<Product> handler) {
        productButtonPressedHandler = handler;
    }

    private void onProductButtonPressed(Product product) {
        if (productButtonPressedHandler != null) {
            productButtonPressedHandler.accept(product);
        }
    }
}
