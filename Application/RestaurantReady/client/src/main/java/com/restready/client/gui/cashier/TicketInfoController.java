package com.restready.client.gui.cashier;

import com.restready.client.gui.PageController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TicketInfoController extends PageController {

    @FXML
    private TreeTableView<ValueWrapper> treeTableView;
    @FXML
    private TreeTableColumn<ValueWrapper, String> seatColumn;
    @FXML
    private TreeTableColumn<ValueWrapper, String> productColumn;
    @FXML
    private TreeTableColumn<ValueWrapper, String> priceColumn;

    @FXML
    private void initialize() {

        //region Configure the table
        seatColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("customerLabel"));
        productColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemText"));
        priceColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("price"));

        seatColumn.setCellFactory(column -> new TreeTableCell<>() {
            @Override
            protected void updateItem(String text, boolean isEmpty) {

                if (isEmpty) {
                    setText("");
                    return;
                }

                TreeItem<ValueWrapper> rowValue = getTableRow().getTreeItem();
                ValueWrapper wrapper = rowValue.getValue();
                Object value = wrapper.value();
                if (value instanceof CustomerOrderItem item) {
                    setText(item.customerLabel());
                } else { // Should be a modification
                    setText("");
                }
            }
        });
        //endregion

        //region CustomerOrderItem[] sampleOrder
        CustomerOrderItem[] sampleOrder = {
                new CustomerOrderItem("Kid's Chicken Fingers", 8.0d)
                        .customerLabel("1"),
                new CustomerOrderItem("Big Boy Burger", 10.0d)
                        .customerLabel("2")
                        .modify("medium")
                        .no("lettuce")
                        .add("bacon", 0.5d)
                        .no("fries", -1.0d)
                        .add("broccoli", 2.0d),
                new CustomerOrderItem("Deluxe Pizza", 14.0d)
                        .customerLabel("3")
                        .no("onions")
                        .add("spinach"),
                new CustomerOrderItem("Mom's Spaghetti", 12.0d)
                        .customerLabel("4")
                        .add("chicken", 4.0d)
                        .modify("linguine")
        };
        //endregion

        //region Populate the table
        TreeItem<ValueWrapper> root = new TreeItem<>(null);
        root.setExpanded(true);
        treeTableView.setRoot(root);
        treeTableView.setShowRoot(false);

        for (CustomerOrderItem item : sampleOrder) {
            TreeItem<ValueWrapper> node = new TreeItem<>(new ValueWrapper(item));
            node.setExpanded(true);
            for (CustomerOrderItem.Modification mod : item.modifications()) {
                node.getChildren().add(new TreeItem<>(new ValueWrapper(mod)));
            }
            root.getChildren().add(node);
        }
        //endregion
    }

    @Getter @Setter
    public static class ValueWrapper {

        private Object value;
        private final ObservableValue<String> customerLabelProperty;
        private final ObservableValue<String> itemTextProperty;
        private final ObservableValue<String> priceProperty;

        public ValueWrapper(Object value) {
            this.value = value;
            if (value instanceof CustomerOrderItem item) {
                customerLabelProperty = new SimpleStringProperty() {
                    @Override
                    public String get() {
                        return item.customerLabel();
                    }
                    @Override
                    public void set(String value) {
                        item.customerLabel(value);
                    }
                };
                itemTextProperty = new SimpleStringProperty() {
                    @Override
                    public String get() {
                        return item.productName();
                    }
                    @Override
                    public void set(String value) {
                        item.productName(value);
                    }
                };
                priceProperty = new PriceProperty(item::price, item::price);
            } else if (value instanceof CustomerOrderItem.Modification modification) {
                customerLabelProperty = new SimpleStringProperty("");
                itemTextProperty = new SimpleStringProperty() {
                    @Override
                    public String get() {
                        return modification.description();
                    }
                    @Override
                    public void set(String value) {
                        modification.description(value);
                    }
                };
                priceProperty = new PriceProperty(modification::price, modification::price);
            } else {
                throw new RuntimeException();
            }
        }

        private static class PriceProperty extends SimpleStringProperty {

            private final Supplier<Double> getter;
            private final Consumer<Double> setter;

            public PriceProperty(Supplier<Double> getter, Consumer<Double> setter) {
                this.getter = getter;
                this.setter = setter;
            }

            @Override
            public String get() {
                NumberFormat currency = NumberFormat.getCurrencyInstance();
                return getter.get() == 0.0d ? "" : currency.format(getter.get());
            }

            @Override
            public void set(String value) {
                setter.accept(Double.parseDouble(value));
            }
        }
    }

    public static class SplitCustomerOrderItem {

        private final CustomerOrderItem[] split;

        public SplitCustomerOrderItem(CustomerOrderItem original, int count) {
            double splitPrice = original.price / count;
            split = new CustomerOrderItem[count];
            for (int i = 0; i < count; ++i) {
                split[i] = new CustomerOrderItem(original.productName(), splitPrice).customerLabel("Split " + (i + 1));
            }
        }
    }

    @Getter @Setter
    public static class CustomerOrderItem {

        //region Fields
        private String customerLabel; // Seat #, name, etc.
        private String productName;
        private double price;
        private final List<Modification> modifications;
        //endregion

        public CustomerOrderItem(String productName, double price) {
            customerLabel = "";
            this.productName = productName;
            this.price = price;
            modifications = new ArrayList<>();
        }

        //region Simple "builder" pattern
        public CustomerOrderItem modify(String mod) {
            modifications.add(new Modification("~" + mod, 0.0d));
            return this;
        }

        public CustomerOrderItem modify(String mod, double price) {
            modifications.add(new Modification("~" + mod, price));
            return this;
        }

        public CustomerOrderItem no(String mod) {
            modifications.add(new Modification("-no " + mod, 0.0d));
            return this;
        }

        public CustomerOrderItem no(String mod, double price) {
            modifications.add(new Modification("-no " + mod, price));
            return this;
        }

        public CustomerOrderItem add(String mod) {
            modifications.add(new Modification("+add " + mod, 0.0d));
            return this;
        }

        public CustomerOrderItem add(String mod, double price) {
            modifications.add(new Modification("+add " + mod, price));
            return this;
        }
        //endregion

        @Getter @Setter
        public static class Modification {

            private String description;
            private double price;

            public Modification(String description, double price) {
                this.description = description;
                this.price = price;
            }
        }
    }
}
