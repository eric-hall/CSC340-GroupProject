package com.restready.gui.cashier;

import com.restready.common.util.Log;
import com.restready.gui.Controller;
import javafx.fxml.FXML;
import javafx.scene.Parent;

public class TicketsOverviewController extends Controller {

    @FXML
    private Parent toolbar;
    @FXML
    private DefaultToolbarController toolbarController;

    @FXML
    public void initialize() {
        toolbarController.setOnHomeButtonPressed(e -> navigateTo(ProfileSelectionController.class));
        toolbarController.setOnBackButtonPressed(e -> navigateTo(OrderEntryController.class));
    }

    @Override
    public void onPageShow() {
        Log.debug(this, "onPageShow");
    }

    @Override
    public void onPageHide() {
        Log.debug(this, "onPageHide");
    }
}
