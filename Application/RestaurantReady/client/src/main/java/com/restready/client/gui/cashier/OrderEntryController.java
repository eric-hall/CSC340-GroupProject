package com.restready.client.gui.cashier;

import com.restready.common.*;
import com.restready.common.util.Log;
import com.restready.client.gui.PageController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.StageStyle;

import java.util.List;
import java.util.Optional;

public class OrderEntryController extends PageController {

    private static final int PRODUCTS_PER_ROW = 6;
    private static final int ROW_HEIGHT = 60; // Pixels

    //region Page content
    private ProductCatalog productCatalog;
    private CashierProfile cashierProfile;
    private CustomerTicket customerTicket;
    private CustomerOrder incomingOrder;
    //region FXML references
    @FXML
    private ListView<CustomerOrderCellData> customerOrderListView;
    @FXML
    private GridPane productGridPane;
    @FXML
    private Button removeButton;
    @FXML
    private Button splitButton;
    @FXML
    private Button labelButton;
    @FXML
    private Button duplicateButton;
    @FXML
    private Button payButton;
    @FXML
    private Button submitButton;
    //endregion
    //endregion

    public OrderEntryController() {
        cashierProfile = new CashierProfile();
        customerTicket = cashierProfile.openNewCustomerTicket();
        incomingOrder = customerTicket.openNewCustomerOrder();
    }

    public void setProductCatalog(ProductCatalog productCatalog) {

        this.productCatalog = productCatalog;

        // Initialize products GridPane.
        productGridPane.getChildren().clear();
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

    //region Event handlers
    @FXML
    private void initialize() {

        customerOrderListView.setCellFactory(caller -> new CustomerOrderItemCell());
        customerOrderListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Set column constraints (per column)
        ColumnConstraints widthLimiter = new ColumnConstraints();
        widthLimiter.setPercentWidth(100.0d / PRODUCTS_PER_ROW);
        for (int i = 0; i < PRODUCTS_PER_ROW; ++i) {
            productGridPane.getColumnConstraints().add(widthLimiter);
        }

        setProductCatalog(ProductCatalog.EXAMPLE_SPACE_THEME_PRODUCT_CATALOG);
    }

    @FXML
    private void onBackButtonPressed() {
        navigateTo(TicketsOverviewController.class);
    }

    @Override
    public void onPageShow() {
        Log.trace(this, "onPageShow");
        customerOrderListView.getSelectionModel().clearSelection();
    }

    @Override
    public void onPageHide() {
        Log.trace(this, "onPageHide");
    }

    @FXML
    private void onRemoveButtonPressed() {
        Log.trace(this, "Remove Button Pressed");
    }

    @FXML
    private void onSplitButtonPressed() {

        Log.trace(this, "Split Button Pressed");

        // Get selected item(s)
        ObservableList<CustomerOrderCellData> selection = getSelectedCustomerOrderItems();
        if (selection.isEmpty()) {
            Log.warn(this, "Cannot split zero-sized selection: button should be disabled");
            return;
        }

        // Show a dialog pop-up to get split count from the user
        String dialogResponse = getUserResponseFromTextEntryDialog("Enter split count:", true);

        // Validate dialog response
        int splitCount;
        try {
            splitCount = Integer.parseInt(dialogResponse);
            if (splitCount < 2) {
                return;
            }
        } catch (NumberFormatException e) {
            Log.error(this, "Dialog expected integer: ", e);
            return;
        }

        splitItems(selection, splitCount);
    }

    @FXML
    private void onLabelButtonPressed() {

        Log.trace(this, "Label Button Pressed");

        // Get selected item(s)
        ObservableList<CustomerOrderCellData> selection = getSelectedCustomerOrderItems();
        if (selection.isEmpty()) {
            Log.warn(this, "Cannot label zero-sized selection");
            return;
        }

        String customerLabelText = getUserResponseFromTextEntryDialog("Enter customer name or number:", false);

        Log.debug(this, "Setting item labels: " + customerLabelText);
        for (CustomerOrderCellData cellData : selection) {
            CustomerOrderItem item = cellData.item();
            item.setCustomerLabel(cellData.labelIndex, customerLabelText);
        }
        customerOrderListView.refresh();
    }

    @FXML
    private void onDuplicateButtonPressed() {
        Log.trace(this, "Duplicate Button Pressed");
    }

    @FXML
    private void onPayButtonPressed() {
        Log.trace(this, "Pay Button Pressed");
    }

    @FXML
    private void onSubmitButtonPressed() {
        Log.trace(this, "Submit Button Pressed");
    }

    private void onProductButtonPressed(Product product) {
        Log.trace(this, String.format("'%s' Button Pressed".formatted(product.getName())));
        CustomerOrderItem item = incomingOrder.addProductToOrder(product);
        customerOrderListView.getItems().add(new CustomerOrderCellData(item, 0, true));
    }
    //endregion

    private void splitItems(ObservableList<CustomerOrderCellData> items, int splitCount) {

        Log.debug(this, "Splitting %d items...".formatted(splitCount));

        ObservableList<CustomerOrderCellData> orderList = customerOrderListView.getItems();
        for (CustomerOrderCellData cellData : items) {

            CustomerOrderItem item = cellData.item();
            String originalLabel = item.getCustomerLabel(cellData.labelIndex);

            // Overwrite the selected item's label
            item.setCustomerLabel(cellData.labelIndex, originalLabel + "(0)");

            // Dupe the selected item's label (splitCount-1 times) and add a list entry for each
            int splitIndexOffset = item.getCustomerLabelsCount();
            for (int i = 0; i < splitCount - 1; ++i) {

                int splitIndex = splitIndexOffset + i;
                String splitLabel = "%s(%d)".formatted(originalLabel, i + 1);
                item.addCustomerLabel(splitLabel);

                CustomerOrderCellData entry = new CustomerOrderCellData(item, splitIndex, cellData.isPartOfIncomingOrder);
                orderList.add(entry);
            }
        }
        customerOrderListView.refresh();
    }

    private String getUserResponseFromTextEntryDialog(String prompt, boolean numeric) {

        TextInputDialog dialog = new TextInputDialog();

        // Borderless window and remove silly default header
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.setContentText(prompt);

        // Filter input for integers only?
        if (numeric) {
            TextField input = dialog.getEditor();
            input.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!"\\d*".matches(newValue)) {
                    input.setText(newValue.replaceAll("\\D", ""));
                }
            });
        }

        Optional<String> userResponse = dialog.showAndWait();
        return userResponse.orElse("");
    }

    private ObservableList<CustomerOrderCellData> getSelectedCustomerOrderItems() {
        return customerOrderListView.getSelectionModel().getSelectedItems();
    }

    private record CustomerOrderCellData(CustomerOrderItem item, int labelIndex, boolean isPartOfIncomingOrder) {

        public boolean isSplitEntry() {
            return item.getCustomerLabelsCount() >= 0;
        }

        public String getCellText() {

            String submissionHint = isPartOfIncomingOrder ? "* " : "";
            String customerLabel = item.getCustomerLabel(labelIndex);
            String productName = item.getProduct().getName();

            if (customerLabel.isBlank()) {
                return "%s %s".formatted(submissionHint, productName);
            }

            return "%s[%s] %s".formatted(submissionHint, customerLabel, productName);
        }
    }

    private static class CustomerOrderItemCell extends ListCell<CustomerOrderCellData> {

        @Override
        protected void updateItem(CustomerOrderCellData item, boolean empty) {

            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
                return;
            }

            setText(String.format(item.getCellText()));
        }
    }
}
