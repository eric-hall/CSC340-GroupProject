package com.restready.gui;

import com.restready.common.util.Log;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.HashMap;

public class PageNavigator {

    private record PageControllerInfo(String fxmlPath, Class<? extends PageController> controllerClass) {
        // Container for lazy initialization info
    }

    private final StackPane _stackContainer;
    private final HashMap<Class<? extends PageController>, PageController> _loadedPages;
    private final HashMap<Class<? extends PageController>, PageControllerInfo> _unloadedPages;
    private PageController _currentPage;

    public PageNavigator(StackPane container) {
        _stackContainer = container;
        _loadedPages = new HashMap<>();
        _unloadedPages = new HashMap<>();
        _currentPage = null;
    }

    public <T extends PageController> void registerPageFXML(Class<T> controllerClass, String fxmlPath) {

        if (_loadedPages.containsKey(controllerClass) || _unloadedPages.containsKey(controllerClass)) {
            Log.error(this, "Cannot register %s multiple times: ".formatted(controllerClass.getSimpleName()));
            return;
        }

        _unloadedPages.put(controllerClass, new PageControllerInfo(fxmlPath, controllerClass));
    }

    public <T extends PageController> void navigateTo(Class<T> controllerClass) {

        PageController page = getOrLoadPage(controllerClass);
        if (page == null) {
            Log.error(this, "Cannot navigate to: "+ controllerClass.getSimpleName());
            return;
        }

        // Notify previously loaded page of the switch
        if (_currentPage != null) {
            _currentPage.onPageHide();
        }

        _stackContainer.getChildren().clear();
        _stackContainer.getChildren().add(page.getRoot());
        page.onPageShow();
        _currentPage = page;
    }

    public <T extends PageController> T getOrLoadPage(Class<T> controllerClass) {

        // Page already loaded?
        PageController page = _loadedPages.getOrDefault(controllerClass, null);
        if (page != null) {
            return controllerClass.cast(page);
        }

        PageControllerInfo info = _unloadedPages.get(controllerClass);
        if (info == null) {
            Log.error(this, "No page found: " + controllerClass.getSimpleName());
            return null;
        }

        // Lazy initialization
        try {
            page = Controller.loadFXML(info.fxmlPath(), info.controllerClass());
            _loadedPages.put(controllerClass, page);
            _unloadedPages.remove(controllerClass);
            page.onLoadedFromFXML(this);
        } catch (IOException e) {
            Log.error(this, "", e);
        }

        return controllerClass.cast(page);
    }
}
