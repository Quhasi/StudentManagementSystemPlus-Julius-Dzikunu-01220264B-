package com.sms.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class SettingsController {
    @FXML private TextField thresholdSettingField;

    @FXML
    private void saveSettings() {
        // In a real app, save to a properties file. For now, just show feedback.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Settings Saved");
        alert.setHeaderText(null);
        alert.setContentText("System settings have been updated locally.");
        alert.showAndWait();
    }
}
