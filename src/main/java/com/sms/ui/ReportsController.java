package com.sms.ui;

import com.sms.domain.Student;
import com.sms.repository.SQLiteStudentRepository;
import com.sms.service.StudentService;
import com.sms.util.CsvUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.util.List;

public class ReportsController {
    @FXML private TableView<Student> topPerformersTable;
    @FXML private TableView<Student> atRiskTable;
    @FXML private Spinner<Integer> topLimitSpinner;
    @FXML private TextField thresholdField;

    private final StudentService studentService = new StudentService(new SQLiteStudentRepository());

    @FXML
    public void initialize() {
        setupTable(topPerformersTable);
        setupTable(atRiskTable);
        topLimitSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 10));
    }

    private void setupTable(TableView<Student> table) {
        TableColumn<Student, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        
        TableColumn<Student, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        
        TableColumn<Student, Double> gpaCol = new TableColumn<>("GPA");
        gpaCol.setCellValueFactory(new PropertyValueFactory<>("gpa"));
        
        table.getColumns().addAll(idCol, nameCol, gpaCol);
    }

    @FXML
    private void generateTopPerformers() {
        int limit = topLimitSpinner.getValue();
        List<Student> top = studentService.getTopPerformers(limit, null, null);
        topPerformersTable.setItems(FXCollections.observableArrayList(top));
    }

    @FXML
    private void exportTopPerformers() {
        try {
            CsvUtil.exportToCsv(topPerformersTable.getItems(), "data/top_performers.csv");
            showAlert("Export Success", "Top performers exported to data/top_performers.csv");
        } catch (IOException e) {
            showAlert("Export Error", e.getMessage());
        }
    }

    @FXML
    private void generateAtRisk() {
        try {
            double threshold = Double.parseDouble(thresholdField.getText());
            List<Student> risk = studentService.getAtRiskStudents(threshold);
            atRiskTable.setItems(FXCollections.observableArrayList(risk));
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Invalid threshold value.");
        }
    }

    @FXML
    private void exportAtRisk() {
        try {
            CsvUtil.exportToCsv(atRiskTable.getItems(), "data/at_risk_students.csv");
            showAlert("Export Success", "At-risk students exported to data/at_risk_students.csv");
        } catch (IOException e) {
            showAlert("Export Error", e.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
