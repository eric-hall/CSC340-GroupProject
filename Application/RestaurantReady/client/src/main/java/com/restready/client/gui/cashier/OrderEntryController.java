package com.restready.client.gui.cashier;

import com.restready.client.gui.admin.ProductCatalogEditorController;
import com.restready.common.*;
import com.restready.common.util.Log;
import com.restready.client.gui.PageController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    //endregion

    //region FXML references
    @FXML
    private ListView<CustomerOrderCellData> customerOrderListView;
    @FXML
    private ProductSelectionController productSelectionController;
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

    public OrderEntryController() {
        cashierProfile = new CashierProfile();
        customerTicket = cashierProfile.openNewCustomerTicket();
        incomingOrder = customerTicket.openNewCustomerOrder();
    }

    public void setProductCatalog(ProductCatalog catalog) {
        productCatalog = catalog;
        productSelectionController.setProductCatalog(catalog);
    }

    //region Event handlers
    @FXML
    private void initialize() {
        customerOrderListView.setCellFactory(caller -> new CustomerOrderItemCell());
        customerOrderListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        productSelectionController.setProductButtonPressedHandler(this::onProductButtonPressed);
    }

    @FXML
    private void onBackButtonPressed() {
//        navigateTo(TicketsOverviewController.class);
        navigateTo(ProductCatalogEditorController.class);
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
        List<CustomerOrderCellData> selection = getSelectedCells();
        if (selection.isEmpty()) {
            Log.warn(this, "Cannot split empty selection: button should be disabled");
            return;
        }

        // Show a dialog pop-up to get split count from the user
        String dialogResponse = textEntryDialog("Enter split count:", true);

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
        List<CustomerOrderCellData> selection = getSelectedCells();
        if (selection.isEmpty()) {
            Log.warn(this, "Cannot label empty selection");
            return;
        }

        String customerLabel = textEntryDialog("Enter customer name or number:", false);

        Log.debug(this, "Setting item labels: '%s'".formatted(customerLabel));
        for (CustomerOrderCellData cellData : selection) {
            CustomerOrderItem item = cellData.item();
            item.setCustomerLabel(cellData.labelIndex, customerLabel);
        }

        customerOrderListView.refresh();
    }

    @FXML
    private void onDuplicateButtonPressed() {

        Log.trace(this, "Duplicate Button Pressed");

        // Get selected item(s)
        List<CustomerOrderCellData> selection = getSelectedCells();
        if (selection.isEmpty()) {
            Log.warn(this, "Cannot dupe empty selection");
            return;
        }

        // Ensure they're all un-submitted, non-split orders
        for (CustomerOrderCellData cell : selection) {
            if (!cell.isPartOfIncomingOrder() || cell.isSplitEntry()) {
                Log.warn(this, "Cannot dupe submitted or split-entry orders");
                return;
            }
        }

        List<CustomerOrderCellData> orderList = customerOrderListView.getItems();
        for (CustomerOrderCellData cell : selection) {

            CustomerOrderItem item = cell.item;
            String label = item.getCustomerLabel(cell.labelIndex);
            if (!label.isBlank()) {
                label += "(dupe)";
            }

            CustomerOrderItem dupe = incomingOrder.addProductToOrder(item.getProduct());
            dupe.setCustomerLabel(0, label);
            orderList.add(new CustomerOrderCellData(dupe, 0, true));
        }
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

    private void splitItems(List<CustomerOrderCellData> items, int splitCount) {

        Log.debug(this, "Splitting %d items".formatted(splitCount));

        List<CustomerOrderCellData> orderList = customerOrderListView.getItems();
        for (CustomerOrderCellData cell : items) {

            CustomerOrderItem item = cell.item();
            String customerLabel = item.getCustomerLabel(cell.labelIndex);

            // Overwrite the selected item's label
            item.setCustomerLabel(cell.labelIndex, customerLabel);

            // Dupe the selected item's label (splitCount-1 times) and add a list entry
            int splitIndexOffset = item.getCustomerLabelsCount();
            for (int i = 0; i < splitCount - 1; ++i) {

                int splitIndex = splitIndexOffset + i;
                item.addCustomerLabel(customerLabel);

                CustomerOrderCellData entry = new CustomerOrderCellData(item, splitIndex, cell.isPartOfIncomingOrder);
                orderList.add(entry);
            }
        }

        customerOrderListView.refresh();
    }

    private String textEntryDialog(String prompt, boolean numeric) {

        TextInputDialog dialog = new TextInputDialog();

        // Borderless window and remove silly default header
        dialog.initStyle(StageStyle.UTILITY);
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
        String response = userResponse.orElse("");
        return response.isBlank() && numeric ? "-1" : response;
    }

    private List<CustomerOrderCellData> getSelectedCells() {
        return customerOrderListView.getSelectionModel().getSelectedItems();
    }

    private record CustomerOrderCellData(CustomerOrderItem item, int labelIndex, boolean isPartOfIncomingOrder) {

        public boolean isSplitEntry() {
            return item.getCustomerLabelsCount() > 1;
        }

        public String getCellText() {

            String submissionHint = isPartOfIncomingOrder ? "* " : "";
            String customerLabel = item.getCustomerLabel(labelIndex);
            String productName = item.getProduct().getName();

            // TODO: Improve how split items are displayed (replace ListView with TreeView?)
            if (isSplitEntry() && customerLabel.isBlank()) {
                return "%s[Split %d] %s".formatted(submissionHint, labelIndex, productName);
            } else if (customerLabel.isBlank()) {
                return "%s %s".formatted(submissionHint, productName);
            } else if (isSplitEntry()) {
                return "%s[%s (Split %d)] %s".formatted(submissionHint, customerLabel, labelIndex, productName);
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
