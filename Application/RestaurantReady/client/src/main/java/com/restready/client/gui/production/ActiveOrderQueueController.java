package com.restready.client.gui.production;

import com.restready.client.gui.PageController;
import com.restready.client.gui.admin.ProductCatalogEditorController;
import com.restready.common.CustomerOrderItem;
import com.restready.common.Product;
import com.restready.common.util.Log;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActiveOrderQueueController extends PageController {

    @FXML
    private TableView<CustomerOrderItem> inProgressTableView, readyTableView;

    private ObservableList<CustomerOrderItem> inProgressOrders, readyOrders;
    private final Map<CustomerOrderItem, Integer> orderToIdMap = new HashMap<>();

    @FXML
    private void initialize() {
        initObservableLists();
        initTableViews();
        populateTableView();
    }

    private void initObservableLists() {
        inProgressOrders = FXCollections.observableArrayList();
        readyOrders = FXCollections.observableArrayList();
    }

    private void initTableViews() {
        initializeTableColumns(inProgressTableView);
        initializeTableColumns(readyTableView);
    }
    private int idCounter = 1;
    private void initializeTableColumns(TableView<CustomerOrderItem> tableView) {
        TableColumn<CustomerOrderItem, String> idColumn = new TableColumn<>("ID");
        TableColumn<CustomerOrderItem, String> orderColumn = new TableColumn<>("Order");

        idColumn.setCellValueFactory(data ->
                new SimpleStringProperty(String.valueOf(getOrGenerateUniqueId(data.getValue())))
        );
        orderColumn.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getProduct().getName())
        );

        // Set a fixed width for the ID column
        idColumn.setPrefWidth(50.0); // Adjust the width as needed

        // Set the order column to use computed size
        orderColumn.prefWidthProperty().bind(tableView.widthProperty().subtract(idColumn.widthProperty()));

        tableView.getColumns().add(idColumn);
        tableView.getColumns().add(orderColumn);
    }



    @Override
    public void onPageShow() {
        // Refresh the lists when the page is shown
        populateTableView();
    }

    private int getOrGenerateUniqueId(CustomerOrderItem order) {
        return orderToIdMap.computeIfAbsent(order, key -> idCounter++);
    }
    public void addOrdersToInProgress(List<CustomerOrderItem> customerOrders) {
        for (CustomerOrderItem order : customerOrders) {
            Product product = order.getProduct();

            inProgressOrders.add(new CustomerOrderItem(order.getOrder(), product));
        }
    }

    @FXML
    private void onBackButtonPressed() {
        navigateTo(ProductCatalogEditorController.class);
    }

    public void populateTableView() {
        for (CustomerOrderItem  order : inProgressOrders) {
            inProgressTableView.getItems().add(order);
        }
        for (CustomerOrderItem  order : readyOrders) {
            readyTableView.getItems().add(order);
        }
    }


    @FXML
    private void onSetAsReadyButtonPressed() {
        moveOrder(inProgressTableView, readyTableView);
    }


    @FXML
    private void onDoneButtonPressed() {
        readyTableView.getItems().remove(readyTableView.getSelectionModel().getSelectedItem());
    }

    private void moveOrder(TableView<CustomerOrderItem> sourceTableView, TableView<CustomerOrderItem> targetTableView) {
        CustomerOrderItem selectedOrder = sourceTableView.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            sourceTableView.getItems().remove(selectedOrder);
            targetTableView.getItems().add(selectedOrder);

            // Remove the order from inProgressOrders
            inProgressOrders.remove(selectedOrder);


            // Log the removed order ID (you can adjust or remove this as needed)
            Log.info("Order removed");
        }
    }

}
