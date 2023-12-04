package com.restready.client.gui.admin;

import com.restready.client.gui.ClientApplication;
import com.restready.client.gui.PageController;
import com.restready.client.gui.cashier.OrderEntryController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.*;

// TODO: Refactor to UserProfilesController
public class EmployeeProfilesController extends PageController {

    private final ObservableList<String> employeeList;
    private final ListView<String> listView;
    private final TextField nameInput;
    private final ComboBox<String> statusComboBox;
    private File currentFile = null;

    public EmployeeProfilesController() {
        employeeList = FXCollections.observableArrayList();
        listView = new ListView<>(employeeList);
        nameInput = new TextField();
        statusComboBox = new ComboBox<>();
    }

    public void initialize(ClientApplication app) {

        BorderPane borderPane = new BorderPane();

        super.initialize(app, borderPane, "EmployeeProfilesController");

        HBox hbox = new HBox();

        Label nameLabel = new Label("Name:");
        Label statusLabel = new Label("Status:");

        Button addButton = new Button("Add Employee");
        addButton.setOnAction(e -> addEmployee());
        Button removeButton = new Button("Remove Employee");
        removeButton.setOnAction(e -> removeEmployee());

        statusComboBox.getItems().addAll("ADMIN", "EMPLOYEE", "KITCHEN");
        statusComboBox.getSelectionModel().selectFirst();

        hbox.getChildren().addAll(nameLabel, nameInput, statusLabel, statusComboBox, addButton, removeButton);
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(10));

        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        MenuItem newItem = new MenuItem("New");
        MenuItem openItem = new MenuItem("Open");
        MenuItem saveItem = new MenuItem("Save");
        newItem.setOnAction(e -> listView.getItems().clear());
        openItem.setOnAction(e -> loadEmployeeDataFromFile());
        saveItem.setOnAction(e -> saveEmployeeDataToFile());
        fileMenu.getItems().addAll(newItem, openItem, saveItem);

        // TODO: Remove this later
        Menu demoMenu = new Menu("Demo");
        MenuItem productCatalogEditorItem = new MenuItem("Go To Products");
        MenuItem orderEntryItem = new MenuItem("Go To Order Entry");
        productCatalogEditorItem.setOnAction(e -> navigateTo(ProductCatalogEditorController.class));
        orderEntryItem.setOnAction(e -> navigateTo(OrderEntryController.class));
        demoMenu.getItems().addAll(productCatalogEditorItem, orderEntryItem);

        menuBar.getMenus().addAll(fileMenu, demoMenu);

        borderPane.setTop(menuBar);
        borderPane.setCenter(hbox);
        borderPane.setBottom(listView);
    }

    private void saveEmployeeDataToFile() {

        if (currentFile == null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Employee File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT Files (*.txt)", "*.txt"));
            currentFile = fileChooser.showSaveDialog(null);
        }

        if (currentFile == null) {
            return;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(currentFile))) {
            for (String employeeInfo : employeeList) {
                writer.println(employeeInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadEmployeeDataFromFile(File file) {

        if (file == null || !file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            employeeList.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                employeeList.add(line);
            }
            currentFile = file;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addEmployee() {

        String name = nameInput.getText();
        String status = statusComboBox.getValue();
        String employeeInfo = name + " - " + status;

        if (!name.isEmpty()) {
            employeeList.add(employeeInfo);
            nameInput.clear();
            statusComboBox.getSelectionModel().selectFirst();
        }
    }

    private void removeEmployee() {

        String selectedEmployee = listView.getSelectionModel().getSelectedItem();

        if (selectedEmployee != null) {
            employeeList.remove(selectedEmployee);
        }
    }

    private void loadEmployeeDataFromFile() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Employee Data File");
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            loadEmployeeDataFromFile(selectedFile);
        }
    }
}
