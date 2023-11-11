module com.restready.gui {

    requires com.restready.common;

    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    exports com.restready.gui;
    exports com.restready.gui.admin;
    exports com.restready.gui.cashier;
    exports com.restready.gui.production;

    opens com.restready.gui to javafx.fxml;
    opens com.restready.gui.admin to javafx.fxml;
    opens com.restready.gui.cashier to javafx.fxml;
    opens com.restready.gui.production to javafx.fxml;
}