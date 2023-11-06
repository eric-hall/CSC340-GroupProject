module com.restready.gui {

    requires com.restready.common;

    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.restready.gui to javafx.fxml;
    exports com.restready.gui;
    exports com.restready.gui.cashier;
    opens com.restready.gui.cashier to javafx.fxml;
}