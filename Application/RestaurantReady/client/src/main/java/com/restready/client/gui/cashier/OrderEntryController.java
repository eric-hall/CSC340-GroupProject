package com.restready.client.gui.cashier;

import com.restready.common.*;
import com.restready.common.util.Log;
import com.restready.client.gui.PageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;

public class OrderEntryController extends PageController {

    private static final int PRODUCTS_PER_ROW = 6;
    private static final int ROW_HEIGHT = 60; // Pixels

    //region Page content
    private final ProductCatalog productCatalog;
    private final CashierProfile cashierProfile;
    private final CustomerTicket customerTicket;
    private final CustomerOrder incomingOrder;
    //endregion

    public OrderEntryController() {
        productCatalog = ProductCatalog.EXAMPLE_SPACE_THEME_PRODUCT_CATALOG;
        cashierProfile = new CashierProfile();
        customerTicket = cashierProfile.openNewCustomerTicket();
        incomingOrder = customerTicket.openNewCustomerOrder();
    }

    //region FXML references
    @FXML
    private ListView<CustomerOrderItem> customerOrderListView;
    @FXML
    private GridPane productGridPane;
    @FXML
    private Button removeButton;
    @FXML
    private Button splitButton;
    @FXML
    private Button modifyButton;
    @FXML
    private Button duplicateButton;
    @FXML
    private Button payButton;
    @FXML
    private Button submitButton;
    //endregion

    //region Event handlers
    @FXML
    public void initialize() {

        customerOrderListView.setCellFactory(x -> new CustomerOrderItemCell());

        // Set column constraints (per column)
        ColumnConstraints widthLimiter = new ColumnConstraints();
        widthLimiter.setPercentWidth(100.0d / PRODUCTS_PER_ROW);
        for (int i = 0; i < PRODUCTS_PER_ROW; ++i) {
            productGridPane.getColumnConstraints().add(widthLimiter);
        }

        // Initialize products GridPane.
        int i = 0;
        for (Product product : productCatalog) {
            Button button = new Button(product.getName());
            button.setTextAlignment(TextAlignment.CENTER);
            button.setWrapText(true);
            button.setPrefSize(Double.MAX_VALUE, ROW_HEIGHT);
            button.setOnAction(e -> onProductButtonPressed(product));
            int x = i % PRODUCTS_PER_ROW;
            int y = i / PRODUCTS_PER_ROW;
            productGridPane.add(button, x, y);
            i += 1;
        }
    }

    @FXML
    private void onBackButtonPressed() {
        navigateTo(TicketsOverviewController.class);
    }

    @Override
    public void onPageShow() {
        Log.debug(this, "onPageShow");
        customerOrderListView.getSelectionModel().clearSelection();
    }

    @Override
    public void onPageHide() {
        Log.debug(this, "onPageHide");
    }

    @FXML
    private void onRemoveButtonPressed() {
        Log.debug(this, "Remove Button Pressed");
    }

    @FXML
    private void onSplitButtonPressed() {
        Log.debug(this, "Split Button Pressed");
    }

    @FXML
    private void onModifyButtonPressed() {
        Log.debug(this, "Modify Button Pressed");
    }

    @FXML
    private void onDuplicateButtonPressed() {
        Log.debug(this, "Duplicate Button Pressed");
    }

    @FXML
    private void onPayButtonPressed() {
        Log.debug(this, "Pay Button Pressed");
    }

    @FXML
    private void onSubmitButtonPressed() {
        Log.debug(this, "Submit Button Pressed");
    }

    private void onProductButtonPressed(Product product) {
        Log.debug(this, String.format("'%s' Button Pressed".formatted(product.getName())));
        CustomerOrderItem item = incomingOrder.addProductToOrder(product);
        customerOrderListView.getItems().add(item);
    }
    //endregion

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
