package com.restready.client.gui.admin;

import com.restready.client.gui.ClientApplication;
import com.restready.client.gui.PageController;
import com.restready.client.gui.cashier.OrderEntryController;
import com.restready.common.Product;
import com.restready.common.ProductCatalog;
import com.restready.common.util.Log;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Pattern;

public class ProductCatalogEditorController extends PageController {

    //region Page content
    private ProductCatalog productCatalog;
    private boolean productCatalogDirty;
    //endregion

    //region FXML field references
    @FXML
    private TableView<Product> productCatalogTableView;
    @FXML
    private TextField productNameTextField;
    @FXML
    private TextField productCategoryTextField;
    @FXML
    private TextField productPriceTextField;
    //endregion

    //region Initialization
    public ProductCatalogEditorController() {
        productCatalog = new ProductCatalog();
        productCatalogDirty = false;
    }

    @FXML
    private void initialize() {

        // Force the table columns to remain at a fixed width
        ReadOnlyDoubleProperty tableWidth = productCatalogTableView.widthProperty();
        int columns = productCatalogTableView.getColumns().size();
        for (TableColumn<Product, ?> column : productCatalogTableView.getColumns()) {
            column.prefWidthProperty().bind(tableWidth.divide(columns));
        }

        productCatalogTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                productNameTextField.setText(newValue.getName());
                productCategoryTextField.setText(newValue.getCategory());
                productPriceTextField.setText(Double.toString(newValue.getPrice()));
            } else { // If no row is selected, clear the input fields
                resetProductTextFields();
            }
        });

        // Format product price text field as double
        StringConverter<Double> converter = new StringConverter<>() {
            @Override
            public Double fromString(String s) {
                if (s.isEmpty() || "-".equals(s) || ".".equals(s) || "-.".equals(s)) {
                    return 0.0d;
                }
                return Double.valueOf(s);
            }
            @Override
            public String toString(Double d) {
                return d.toString();
            }
        };

        // TODO: Limit to 2 decimal places?
        Pattern pattern = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");
        TextFormatter<Double> textFormatter = new TextFormatter<>(converter, 0.0d, filter -> {
            if (pattern.matcher(filter.getControlNewText()).matches()) {
                return filter;
            }
            return null;
        });
        productPriceTextField.setTextFormatter(textFormatter);

        // TODO: Remove later...
        try {
            URL url = ClientApplication.class.getResource("/temp/restaurant-menu.txt");
            if (url != null) {
                productCatalog = ProductCatalogSaveLoad.loadFrom(new File(url.toURI()));
                for (Product product : productCatalog) {
                    productCatalogTableView.getItems().add(product);
                }
            }
        } catch (URISyntaxException e) {
            Log.error(this, "Demo failed: Can't load sample menu", e);
        }
    }
    //endregion

    //region MenuBar event handlers
    @FXML
    private void onMenuNewFile() {

        if (!confirmChangesMade()) {
            return;
        }

        productCatalog = new ProductCatalog();
        resetProductTable();
        resetProductTextFields();
    }

    @FXML
    private void onMenuOpenFile() {

        if (!confirmChangesMade()) {
            return;
        }

        // Allow user the pick a file from their system
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Product Catalog File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT Files (*.txt)", "*.txt"));
        File file = fileChooser.showOpenDialog(null);
        if (file == null) {
            return;
        }

        resetProductTable();
        resetProductTextFields();

        productCatalog = ProductCatalogSaveLoad.loadFrom(file);
        for (Product product : productCatalog) {
            productCatalogTableView.getItems().add(product);
        }
    }

    @FXML
    private void onMenuSaveFile() {

        // Allow user the pick a file from their system
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Product Catalog File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT Files (*.txt)", "*.txt"));
        File file = fileChooser.showSaveDialog(null);
        if (file == null) {
            return;
        }

        ProductCatalogSaveLoad.saveTo(file, productCatalog);
        productCatalogDirty = false;
    }

    @FXML // TODO: Remove later (demo)
    public void onMenuGoToEmployees() {
        navigateTo(EmployeeProfilesController.class);
    }

    @FXML // TODO: Remove later (demo)
    public void onMenuGoToOrderEntry() {
        OrderEntryController page = getOrLoadPage(OrderEntryController.class);
        page.setProductCatalog(productCatalog);
        navigateTo(OrderEntryController.class);
    }
    //endregion

    //region Product editing Button event handlers
    @FXML
    public void onAddProductPressed() {

        Product product = createProductFromTextFields();
        if (product == null) {
            return;
        }

        resetProductTextFields();

        productCatalogTableView.getSelectionModel().clearSelection();
        productCatalogTableView.getItems().add(product);
        productCatalog.addProduct(product);
        productCatalogDirty = true;
    }

    @FXML
    public void onUpdateProductPressed() {

        Product product = productCatalogTableView.getSelectionModel().getSelectedItem();
        if (product == null) {
            return;
        }

        // Updated in-place
        updateProductFromTextFields(product);
        productCatalogTableView.refresh();
        productCatalogDirty = true;
    }

    @FXML
    public void onRemoveProductPressed() {

        Product product = productCatalogTableView.getSelectionModel().getSelectedItem();
        if (product == null) {
            return;
        }

        productCatalogTableView.getSelectionModel().clearSelection();
        productCatalogTableView.getItems().remove(product);
        if (!productCatalog.removeProduct(product.getId())) {
            Log.error(this, "Product not removed: " + product.getName());
            throw new RuntimeException();
        }
        productCatalogDirty = true;
    }
    //endregion

    //region Helper methods
    private boolean confirmChangesMade() {

        if (!productCatalogDirty) {
            return true;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Changes made. Discard?", ButtonType.OK, ButtonType.CANCEL);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.showAndWait();
        return alert.getResult() != ButtonType.CANCEL;
    }

    private void resetProductTable() {
        productCatalogTableView.getSelectionModel().clearSelection();
        productCatalogTableView.getItems().clear();
    }

    private void resetProductTextFields() {
        productNameTextField.clear();
        productCategoryTextField.clear();
        productPriceTextField.clear();
        getRoot().requestFocus(); // Clear any control focus
    }

    private void updateProductFromTextFields(Product product) {

        String name = productNameTextField.getText();
        String category = productCategoryTextField.getText();
        String price = productPriceTextField.getText();

        if (name.isBlank() || category.isBlank() || price.isBlank()) {
            return;
        }

        product.setName(name);
        product.setCategory(category);
        product.setPrice(Double.parseDouble(price));
    }

    private Product createProductFromTextFields() {

        String name = productNameTextField.getText();
        String category = productCategoryTextField.getText();
        String price = productPriceTextField.getText();

        if (name.isBlank() || category.isBlank() || price.isBlank()) {
            return null;
        }

        Product product = new Product();
        product.setName(name);
        product.setCategory(category);
        product.setPrice(Double.parseDouble(price));
        return product;
    }
    //endregion
}
