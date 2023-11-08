package com.restready.gui.cashier;

import com.restready.common.util.Log;
import com.restready.gui.Controller;
import com.restready.gui.PageNavigator;
import javafx.fxml.FXML;
import javafx.scene.Parent;

public class OrderEntryController extends Controller {

    @FXML
    private Parent defaultToolbar;
    @FXML
    private DefaultToolbarController defaultToolbarController;

    @FXML
    public void initialize() {
    }

    @Override
    public void onLoadedFromFXML(PageNavigator navigator) {
        defaultToolbarController.setOnToolbarHomeButtonPressed(e -> navigator.navigateTo(ProfileSelectionController.class));
        defaultToolbarController.setOnToolbarBackButtonPressed(e -> navigator.navigateTo(TicketsOverviewController.class));
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
