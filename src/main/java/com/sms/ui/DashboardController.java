package com.sms.ui;

import com.sms.domain.Student;
import com.sms.repository.SQLiteStudentRepository;
import com.sms.service.StudentService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.util.List;

public class DashboardController {
    @FXML private Label totalStudentsLabel;
    @FXML private Label activeStudentsLabel;
    @FXML private Label inactiveStudentsLabel;
    @FXML private Label averageGpaLabel;

    private final StudentService studentService = new StudentService(new SQLiteStudentRepository());

    @FXML
    public void initialize() {
        refreshStats();
    }

    public void refreshStats() {
        List<Student> students = studentService.getAllStudents();
        totalStudentsLabel.setText(String.valueOf(students.size()));
        
        long activeCount = students.stream().filter(s -> s.getStatus().name().equals("ACTIVE")).count();
        activeStudentsLabel.setText(String.valueOf(activeCount));
        inactiveStudentsLabel.setText(String.valueOf(students.size() - activeCount));
        
        double avgGpa = students.stream().mapToDouble(Student::getGpa).average().orElse(0.0);
        averageGpaLabel.setText(String.format("%.2f", avgGpa));
    }
}
