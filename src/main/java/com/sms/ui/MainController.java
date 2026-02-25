package com.sms.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import java.io.IOException;

public class MainController {
    @FXML private StackPane contentArea;

    @FXML
    public void initialize() {
        showDashboard();
    }

    @FXML
    public void showDashboard() {
        loadView("/com/sms/views/Dashboard.fxml");
    }

    @FXML
    public void showStudents() {
        loadView("/com/sms/views/Students.fxml");
    }

    @FXML
    public void showReports() {
        loadView("/com/sms/views/Reports.fxml");
    }

    @FXML
    public void showImportExport() {
        loadView("/com/sms/views/ImportExport.fxml");
    }

    @FXML
    public void showSettings() {
        loadView("/com/sms/views/Settings.fxml");
    }

    private void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            if (loader.getLocation() == null) {
                throw new IOException("FXML file not found: " + fxmlPath);
            }
            Parent view = loader.load();
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Navigation Error");
            alert.setHeaderText("Could not load view");
            alert.setContentText("Error loading: " + fxmlPath + "\n" + e.getMessage());
            alert.showAndWait();
        }
    }
}
