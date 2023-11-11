module com.restready.client {

    requires com.restready.common;

    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    exports com.restready.client.gui;
    exports com.restready.client.gui.admin;
    exports com.restready.client.gui.cashier;
    exports com.restready.client.gui.production;

    opens com.restready.client.gui.admin to javafx.fxml;
    opens com.restready.client.gui.cashier to javafx.fxml;
    opens com.restready.client.gui.production to javafx.fxml;
    opens com.restready.client.gui to javafx.fxml;
}