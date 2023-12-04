package com.restready.client.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

/**
 * Base class for custom GUI controllers.
 */
public abstract class PageController {

    private ClientApplication _app;
    private Parent _root;
    private String _name;

    public void initialize(ClientApplication app, Parent root, String name) {
        _app = app;
        _root = root;
        _name = name;
    }

    public final Parent getRoot() {
        return _root;
    }

    public final String getName() {
        return _name;
    }

    public final <T extends PageController> T getOrLoadPage(Class<T> page) {
        return _app.getOrLoadPage(page);
    }

    public final <T extends PageController> void navigateTo(Class<T> page) {
        _app.navigateTo(page);
    }

    public void onPageShow() {
    }

    public void onPageHide() {
    }

    /**
     * Loads an FXML document and returns a reference to its Controller.
     *
     * @throws IOException The FXML could not be loaded.
     */
    public static <T extends PageController> T loadFXML(ClientApplication app, String fxml, Class<T> controllerClass) throws IOException {

        // Load the FXML
        URL url = ClientApplication.class.getResource(fxml);
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent root = fxmlLoader.load();

        String name = controllerClass.getSimpleName();

        // Initialize (internal) and return
        PageController page = fxmlLoader.getController();
        page.initialize(app, root, name);
        return controllerClass.cast(page);
    }
}
