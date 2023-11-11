package com.restready.client.gui.cashier;

import com.restready.common.util.Log;
import com.restready.client.gui.PageController;
import javafx.fxml.FXML;
import javafx.scene.Parent;

public class OrderEntryController extends PageController {

    @FXML
    private Parent toolbar;
    @FXML
    private DefaultToolbarController toolbarController;

    @FXML
    public void initialize() {
        toolbarController.setOnHomeButtonPressed(e -> navigateTo(ProfileSelectionController.class));
        toolbarController.setOnBackButtonPressed(e -> navigateTo(TicketsOverviewController.class));
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
