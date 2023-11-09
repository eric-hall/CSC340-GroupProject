package com.restready.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

/**
 * Base class for custom GUI controllers.
 */
public abstract class Controller {

    private ClientApplication _app;
    private Parent _root;
    private String _name;

    private void initialize(ClientApplication app, Parent root, String name) {
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

    public final <T extends Controller> void navigateTo(Class<T> page) {
        _app.navigateTo(page);
    }

    public final <T extends Controller> T getOrLoadPage(Class<T> page) {
        return _app.getOrLoadPage(page);
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
    public static <T extends Controller> T loadFXML(ClientApplication app, String fxml, Class<T> controllerClass) throws IOException {

        // Load the FXML
        URL url = ClientApplication.class.getResource(fxml);
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent root = fxmlLoader.load();

        String name = controllerClass.getSimpleName();

        // Initialize (internal) and return
        Controller controller = fxmlLoader.getController();
        controller.initialize(app, root, name);
        return controllerClass.cast(controller);
    }
}
