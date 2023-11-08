package com.restready.gui.cashier;

import com.restready.common.util.Log;
import com.restready.gui.PageController;
import javafx.fxml.FXML;
import javafx.scene.Parent;

public class ProfileSelectionController extends PageController {

    @FXML
    private Parent toolbar;
    @FXML
    private DefaultToolbarController toolbarController;

    @FXML
    public void initialize() {
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
