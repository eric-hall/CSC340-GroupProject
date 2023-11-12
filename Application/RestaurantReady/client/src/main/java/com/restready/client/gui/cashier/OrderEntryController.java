package com.restready.client.gui.cashier;

import com.restready.common.*;
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
        String[] spaceMenuOptions = {
                "Cosmic Pizza", "Nebula Noodles", "Star Squid", "Sky Skewers",
                "Meteor Mac", "Apollo Appetizer", "Astro Burger", "Solar Sushi",
                "Space Sliders", "Veggie Voyager", "Black Hole Bites", "Solar Stir-Fry",
                "Alien Toast", "Satellite Salad", "Martian Melt", "Quasar Queso",
                "Galactic Grill", "Rocket Bowl", "Interstellar Pasta", "Comet Croissant"
        };
        for (String productName : spaceMenuOptions) {
            EXAMPLE_PRODUCT_CATALOG.addProduct(new Product(productName, 0.0d));
        }
    }

    private static final int PRODUCTS_PER_ROW = 6;
    private static final int ROW_HEIGHT = 60; // Pixels

    //region Page content
    private final ProductCatalog productCatalog = EXAMPLE_PRODUCT_CATALOG;
    private final CashierProfile cashierProfile = new CashierProfile();
    private final CustomerTicket customerTicket = new CustomerTicket(cashierProfile);
    private final CustomerOrder incomingOrder = customerTicket.openNewCustomerOrder();
    //endregion

    //region FXML references
    @FXML
    private ListView<CustomerOrderItem> customerOrderListView;
    @FXML
    private GridPane productGridPane;
    //endregion

    @FXML
    public void initialize() {

        customerOrderListView.setCellFactory(x -> new CustomerOrderItemCell());

        // Set column constraints (per column)
        ColumnConstraints widthLimiter = new ColumnConstraints();
        widthLimiter.setPercentWidth(100.0d / PRODUCTS_PER_ROW);
        for (int i = 0; i < PRODUCTS_PER_ROW; ++i) {
            productGridPane.getColumnConstraints().add(widthLimiter);
        }

        // Initialize products GridPane: each cell button references a Product.
        int i = 0;
        for (Product product : productCatalog) {
            int x = i % PRODUCTS_PER_ROW;
            int y = i / PRODUCTS_PER_ROW;
            i += 1;
            addProductOptionToGrid(product, x, y);
        }
    }

    //region Event handlers
    @Override
    public void onPageShow() {
        Log.debug(this, "onPageShow");
        customerOrderListView.getSelectionModel().clearSelection();
    }

    @Override
    public void onPageHide() {
        Log.debug(this, "onPageHide");
    }

    private void handleProductButtonPressed(Product product) {
        Log.debug(this, String.format("'%s' Button Pressed".formatted(product.getName())));
        CustomerOrderItem item = incomingOrder.addProductToOrder(product);
        customerOrderListView.getItems().add(item);
    }
    //endregion

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
                Product product = item.getProduct();
                setText(String.format("%s - %s", item.getLabel(), product.getName()));
            }
        }
    }
}
