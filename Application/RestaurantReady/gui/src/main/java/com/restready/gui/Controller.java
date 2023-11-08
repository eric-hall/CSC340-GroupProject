package com.restready.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

/**
 * Base class for custom GUI controllers.
 */
public abstract class Controller {

    private Parent _root;
    private String _name;

    private void initialize(Parent root, String name) {
        _root = root;
        _name = name;
    }

    public final Parent getRoot() {
        return _root;
    }

    public final String getName() {
        return _name;
    }

    public void onLoadedFromFXML(PageNavigator navigator) {
    }

    public void onPageShow() {
    }

    public void onPageHide() {
    }

    public static String toName(Class<? extends Controller> controllerClass) {
        // Remove 'Controller' from end of class name
        String name = controllerClass.getSimpleName();
        return name.replaceAll("Controller$", "");
    }

    /**
     * Loads an FXML document and returns a reference to its Controller.
     *
     * @throws IOException The FXML could not be loaded.
     */
    public static <T extends Controller> T loadFXML(String fxml, Class<T> controllerClass) throws IOException {

        // Load the FXML
        URL url = ClientApplication.class.getResource(fxml);
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent root = fxmlLoader.load();

        // Remove 'Controller' from end of class name
        String name = toName(controllerClass);

        // Initialize (internal) and return
        Controller controller = fxmlLoader.getController();
        controller.initialize(root, name);
        return controllerClass.cast(controller);
    }
}
