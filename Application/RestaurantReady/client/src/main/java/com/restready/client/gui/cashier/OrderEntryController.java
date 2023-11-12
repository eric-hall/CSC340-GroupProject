package com.restready.client.gui.cashier;

import com.restready.common.CustomerOrderItem;
import com.restready.common.Product;
import com.restready.common.ProductCatalog;
import com.restready.common.util.Log;
import com.restready.client.gui.PageController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;

public class OrderEntryController extends PageController {

    // TODO: Remove later
    private static final ProductCatalog EXAMPLE_PRODUCT_CATALOG;
    static {
        EXAMPLE_PRODUCT_CATALOG = new ProductCatalog();
        String[] productNames = {
                "Burger", "Pizza", "Mom's Spaghetti",
                "Dad's Spaghetti", "Chicken Plate", "Salad",
                "Ravioli", "Beef Special", "Sam's Hot-dog",
                "Nachos", "Daily Soup", "Arancini"
        };
        for (int i = 0; i < productNames.length; ++i) {
            EXAMPLE_PRODUCT_CATALOG.addProduct(new Product(i, productNames[i], 0.0d));
        }
    }

    private static final int PRODUCTS_PER_ROW = 6;
    private static final int ROW_HEIGHT = 60; // Pixels

    @FXML
    private ListView<CustomerOrderItem> customerOrderListView;
    @FXML
    private GridPane productGridPane;

    @FXML
    public void initialize() {

        customerOrderListView.setCellFactory(x -> new CustomerOrderItemCell());

        // Set column constraints (per column)
        ColumnConstraints widthLimiter = new ColumnConstraints();
        widthLimiter.setPercentWidth(100.0d / PRODUCTS_PER_ROW);
        for (int i = 0; i < PRODUCTS_PER_ROW; ++i) {
            productGridPane.getColumnConstraints().add(widthLimiter);
        }

        // Initialize products GridPane: each cell contains a button referencing a Product.
        Product[] products = EXAMPLE_PRODUCT_CATALOG.getProducts().values().toArray(new Product[0]);
        for (int i = 0; i < products.length; ++i) {
            int x = i % PRODUCTS_PER_ROW;
            int y = i / PRODUCTS_PER_ROW;
            addProductOptionToGrid(products[i], x, y);
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

    private void handleProductButtonPressed(Product product) {
        Log.info(this, product.getName() + " Pressed!");
        CustomerOrderItem item = new CustomerOrderItem(-1, product.getId(), -1, ""); // TODO: Use real id's
        customerOrderListView.getItems().add(item);
    }

    private void addProductOptionToGrid(Product product, int x, int y) {
        Button button = new Button(product.getName());
        button.setTextAlignment(TextAlignment.CENTER);
        button.setWrapText(true);
        button.setPrefSize(Double.MAX_VALUE, ROW_HEIGHT);
        button.setOnAction(e -> handleProductButtonPressed(product));
        productGridPane.add(button, x, y);
    }

    private static class CustomerOrderItemCell extends ListCell<CustomerOrderItem> {
        @Override
        protected void updateItem(CustomerOrderItem item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
            } else {
                Product product = EXAMPLE_PRODUCT_CATALOG.getProduct(item.getProductId());
                setText(String.format("%s - %s", item.getLabel(), product.getName()));
            }
        }
    }
}
