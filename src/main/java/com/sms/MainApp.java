package com.sms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import com.sms.util.LoggerUtil;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            LoggerUtil.log("Application starting...");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sms/views/MainLayout.fxml"));
            Parent root = loader.load();
            
            primaryStage.setTitle("Student Management System Plus");
            primaryStage.setScene(new Scene(root, 1000, 700));
            primaryStage.show();
        } catch (Exception e) {
            LoggerUtil.log("FATAL ERROR: Failed to load UI. " + e.getMessage());
            e.printStackTrace();
            // Show a simple error if FXML fails
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Startup Error");
            alert.setHeaderText("Could not load application interface");
            alert.setContentText("Please ensure all FXML files are in the correct resources folder.\n\nError: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @Override
    public void stop() {
        LoggerUtil.log("Application closing...");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
