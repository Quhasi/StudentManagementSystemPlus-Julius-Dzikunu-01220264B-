package com.sms.ui;

import com.sms.domain.Student;
import com.sms.domain.StudentStatus;
import com.sms.repository.SQLiteStudentRepository;
import com.sms.service.StudentService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class StudentFormController {
    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField programmeField;
    @FXML private ComboBox<Integer> levelCombo;
    @FXML private TextField gpaField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private ComboBox<StudentStatus> statusCombo;

    private final StudentService studentService = new StudentService(new SQLiteStudentRepository());
    private boolean saveClicked = false;
    private Student student;

    @FXML
    public void initialize() {
        levelCombo.setItems(FXCollections.observableArrayList(100, 200, 300, 400, 500, 600, 700));
        statusCombo.setItems(FXCollections.observableArrayList(StudentStatus.values()));
        statusCombo.setValue(StudentStatus.ACTIVE);
    }

    public void setStudent(Student student) {
        this.student = student;
        if (student != null) {
            idField.setText(student.getStudentId());
            idField.setEditable(false); // ID cannot be changed on edit
            nameField.setText(student.getFullName());
            programmeField.setText(student.getProgramme());
            levelCombo.setValue(student.getLevel());
            gpaField.setText(String.valueOf(student.getGpa()));
            emailField.setText(student.getEmail());
            phoneField.setText(student.getPhoneNumber());
            statusCombo.setValue(student.getStatus());
        }
    }

    public boolean isSaveClicked() {
        return saveClicked;
    }

    @FXML
    private void handleSave() {
        try {
            if (student == null) {
                student = new Student();
            }
            
            student.setStudentId(idField.getText());
            student.setFullName(nameField.getText());
            student.setProgramme(programmeField.getText());
            student.setLevel(levelCombo.getValue() != null ? levelCombo.getValue() : 0);
            student.setGpa(Double.parseDouble(gpaField.getText()));
            student.setEmail(emailField.getText());
            student.setPhoneNumber(phoneField.getText());
            student.setStatus(statusCombo.getValue());

            if (idField.isEditable()) {
                studentService.addStudent(student);
            } else {
                studentService.updateStudent(student);
            }

            saveClicked = true;
            closeStage();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void handleCancel() {
        closeStage();
    }

    private void closeStage() {
        Stage stage = (Stage) idField.getScene().getWindow();
        stage.close();
    }
}
