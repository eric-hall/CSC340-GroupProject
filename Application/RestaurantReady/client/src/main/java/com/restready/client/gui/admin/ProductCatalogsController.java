package com.restready.client.gui.admin;

import com.restready.client.gui.PageController;
import com.restready.common.Product;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ProductCatalogsController extends PageController {

    //region FXML references
    @FXML
    private ListView<Product> productListView;
    @FXML
    private TextField nameInput;
    @FXML
    private TextField categoryInput;
    @FXML
    private TextField priceInput;
    //endregion

    //region Event handlers
    @FXML
    private void initialize() {
        productListView.setCellFactory(productListView -> new ListCell<>() {
            @Override
            protected void updateItem(Product product, boolean isEmpty) {
                super.updateItem(product, isEmpty);
                if (isEmpty || product == null) {
                    setText(null);
                } else {
                    setText("[%s] %s $%.2f".formatted(product.getCategory(), product.getName(), product.getPrice()));
                }
            }
        });
        productListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                nameInput.setText(newValue.getName());
                categoryInput.setText(newValue.getCategory());
                priceInput.setText(Double.toString(newValue.getPrice()));
            } else {
                clearInputFields();
            }
        });
    }

    @FXML
    private void onAddProductButtonPressed() {
        Product newProduct = getProductFromInputFields();
        if (newProduct != null) {
            productListView.getItems().add(newProduct);
            clearInputFields();
        }
    }

    @FXML
    private void onUpdateProductButtonPressed() {
        int selectedIndex = productListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Product updatedProduct = getProductFromInputFields();
            if (updatedProduct != null) {
                productListView.getItems().set(selectedIndex, updatedProduct);
                clearInputFields();
            }
        }
    }

    @FXML
    private void onRemoveProductButtonPressed() {
        int selectedIndex = productListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            productListView.getItems().remove(selectedIndex);
            clearInputFields();
        }
    }
    //endregion

    private Product getProductFromInputFields() {

        String name = nameInput.getText();
        String category = categoryInput.getText();
        String priceText = priceInput.getText();

        if (name.isBlank() || category.isBlank() || priceText.isBlank()) {
            return null;
        }

        try {
            double price = Double.parseDouble(priceText);
            Product newProduct = new Product();
            newProduct.setName(name);
            newProduct.setCategory(category);
            newProduct.setPrice(price);
            return newProduct;
        } catch (NumberFormatException e) {
            // Handle invalid price format
            return null;
        }
    }

    private void clearInputFields() {
        nameInput.clear();
        categoryInput.clear();
        priceInput.clear();
        productListView.getSelectionModel().clearSelection();
    }
}
