package com.restready.gui;

import com.restready.common.util.Log;
import com.restready.gui.cashier.OrderEntryController;
import com.restready.gui.cashier.ProfileSelectionController;
import com.restready.gui.cashier.TicketsOverviewController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ClientApplication extends Application {

    private record ControllerInfo(String fxmlPath, Class<? extends Controller> controllerClass) {
        // Container for lazy initialization info
    }

    private final Scene _mainScene;
    private final Map<Class<? extends Controller>, Controller> _loadedPages;
    private final Map<Class<? extends Controller>, ControllerInfo> _unloadedPages;
    private Controller _currentPage;

    public ClientApplication() {
        _mainScene = new Scene(new Pane(), 800, 600);
        _loadedPages = new HashMap<>();
        _unloadedPages = new HashMap<>();
        _currentPage = null;
    }

    @Override
    public void start(Stage stage) {

        registerPageFXML(
                ProfileSelectionController.class,
                "/fxml/cashier/profile-selection.fxml");
        registerPageFXML(
                TicketsOverviewController.class,
                "/fxml/cashier/tickets-overview.fxml");
        registerPageFXML(
                OrderEntryController.class,
                "/fxml/cashier/order-entry.fxml");

        // Open the first page
        navigateTo(OrderEntryController.class);

        // Initialize the stage
        stage.setTitle("Restaurant Ready!");
        stage.setScene(_mainScene);
        stage.show();
    }

    public <T extends Controller> void registerPageFXML(Class<T> controllerClass, String fxmlPath) {

        if (_loadedPages.containsKey(controllerClass) || _unloadedPages.containsKey(controllerClass)) {
            Log.error(this, "Cannot register %s multiple times: ".formatted(controllerClass.getSimpleName()));
            return;
        }

        _unloadedPages.put(controllerClass, new ControllerInfo(fxmlPath, controllerClass));
    }

    public <T extends Controller> T getOrLoadPage(Class<T> controllerClass) {

        // Page already loaded?
        Controller page = _loadedPages.getOrDefault(controllerClass, null);
        if (page != null) {
            return controllerClass.cast(page);
        }

        ControllerInfo info = _unloadedPages.get(controllerClass);
        if (info == null) {
            Log.error(this, "No page found: " + controllerClass.getSimpleName());
            return null;
        }

        // Lazy initialization
        try {
            page = Controller.loadFXML(this, info.fxmlPath(), info.controllerClass());
            _loadedPages.put(controllerClass, page);
            _unloadedPages.remove(controllerClass);
        } catch (IOException e) {
            Log.error(this, "", e);
        }

        return controllerClass.cast(page);
    }

    public <T extends Controller> void navigateTo(Class<T> controllerClass) {

        Controller page = getOrLoadPage(controllerClass);
        if (page == null) {
            Log.error(this, "Cannot navigate to: "+ controllerClass.getSimpleName());
            return;
        }

        if (_currentPage != null) {
            _currentPage.onPageHide();
        }

        _mainScene.setRoot(page.getRoot());
        _currentPage = page;
        page.onPageShow();
    }

    public static void main(String[] args) {
        launch();
    }
}
