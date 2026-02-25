package com.sms.ui;

import com.sms.domain.Student;
import com.sms.repository.SQLiteStudentRepository;
import com.sms.service.StudentService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class StudentController {
    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, String> idCol;
    @FXML private TableColumn<Student, String> nameCol;
    @FXML private TableColumn<Student, String> progCol;
    @FXML private TableColumn<Student, Double> gpaCol;
    
    @FXML private TextField searchField;
    @FXML private ComboBox<String> levelFilter;
    
    private final StudentService studentService = new StudentService(new SQLiteStudentRepository());

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        progCol.setCellValueFactory(new PropertyValueFactory<>("programme"));
        gpaCol.setCellValueFactory(new PropertyValueFactory<>("gpa"));
        
        loadStudents();
    }

    @FXML
    public void loadStudents() {
        studentTable.setItems(FXCollections.observableArrayList(studentService.getAllStudents()));
    }

    @FXML
    private void handleSearch() {
        String query = searchField.getText();
        studentTable.setItems(FXCollections.observableArrayList(studentService.searchStudents(query)));
    }

    @FXML
    private void handleAdd() {
        showStudentForm(null);
    }

    @FXML
    private void handleEdit() {
        Student selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showStudentForm(selected);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Student Selected");
            alert.setContentText("Please select a student in the table.");
            alert.showAndWait();
        }
    }

    private void showStudentForm(Student student) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sms/views/StudentForm.fxml"));
            Parent page = loader.load();
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle(student == null ? "Add Student" : "Edit Student");
            dialogStage.initModality(javafx.stage.Modality.WINDOW_MODAL);
            dialogStage.initOwner(studentTable.getScene().getWindow());
            dialogStage.setScene(new javafx.scene.Scene(page));
            
            StudentFormController controller = loader.getController();
            controller.setStudent(student);
            
            dialogStage.showAndWait();
            
            if (controller.isSaveClicked()) {
                loadStudents();
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDelete() {
        Student selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete student " + selected.getFullName() + "?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    studentService.deleteStudent(selected.getStudentId());
                    loadStudents();
                }
            });
        }
    }
}
