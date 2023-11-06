package com.restready.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

/**
 * Base class for custom GUI controllers.
 * Contains common metadata needed for communication between controllers.
 * Also contains optional lifecycle methods to be overridden.
 */
public abstract class Controller {

    private Parent _pageRoot;
    private String _pageName;

    public final Parent getPageRoot() {
        return _pageRoot;
    }

    public final String getPageName() {
        return _pageName;
    }

    private void initialize(Parent page, String name) {
        _pageRoot = page;
        _pageName = name;
    }

    /**
     * Loads an FXML document and returns its controller: an instance of ControllerBase.
     *
     * @throws IOException if the FXML could not be loaded.
     */
    public static <T extends Controller> T loadFXML(String fxml, Class<T> controllerClass) throws IOException {

        // Load the FXML
        URL url = ClientApplication.class.getResource(fxml);
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent page = fxmlLoader.load();

        // Remove 'Controller' from end of class name
        String name = controllerClass.getName();
        name = name.replaceFirst("/(?=[A-Z][^A-Z]+$)/", "");

        // Initialize and return...
        Controller controller = fxmlLoader.getController();
        controller.initialize(page, name);
        return controllerClass.cast(controller);
    }
}
