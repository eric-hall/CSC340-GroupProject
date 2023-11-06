package com.restready.gui.cashier;

import com.restready.common.util.Log;
import com.restready.gui.Controller;
import com.restready.gui.PageNavigator;
import javafx.fxml.FXML;
import javafx.scene.Parent;

public class TicketsOverviewController extends Controller {

    @FXML
    private Parent defaultToolbar;
    @FXML
    private DefaultToolbarController defaultToolbarController;

    @FXML
    public void initialize() {
        defaultToolbarController.setOnToolbarBackButtonPressed(e -> Log.info(getName(), "Back button pressed!"));
        defaultToolbarController.setOnToolbarHomeButtonPressed(e -> Log.info(getName(), "Home button pressed!"));
    }

    @Override
    public void onPageLoaded(PageNavigator navigator) {
        OrderEntryController otherPage = navigator.getOrLoadPage(OrderEntryController.class);
        Log.debug(this, "Reference acquired: " + otherPage.getName());
    }
}
