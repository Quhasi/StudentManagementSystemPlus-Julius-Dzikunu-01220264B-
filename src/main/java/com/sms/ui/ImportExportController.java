package com.sms.ui;

import com.sms.domain.Student;
import com.sms.repository.SQLiteStudentRepository;
import com.sms.service.StudentService;
import com.sms.util.CsvUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImportExportController {
    @FXML private Label importStatusLabel;
    @FXML private Label exportStatusLabel;
    @FXML private TextArea importLogArea;

    private final StudentService studentService = new StudentService(new SQLiteStudentRepository());

    @FXML
    private void handleImport() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showOpenDialog(null);
        
        if (file != null) {
            try {
                List<String> errors = new ArrayList<>();
                List<Student> students = CsvUtil.importFromCsv(file.getAbsolutePath(), errors);
                
                int successCount = 0;
                for (Student s : students) {
                    try {
                        studentService.addStudent(s);
                        successCount++;
                    } catch (Exception e) {
                        errors.add("Student " + s.getStudentId() + ": " + e.getMessage());
                    }
                }
                
                importStatusLabel.setText("Imported " + successCount + " students. " + errors.size() + " errors.");
                importLogArea.setText(String.join("\n", errors));
                
                if (!errors.isEmpty()) {
                    CsvUtil.exportToCsv(new ArrayList<>(), "data/import_errors.csv"); // Simplified error export
                }
            } catch (IOException e) {
                importStatusLabel.setText("Error: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleExport() {
        try {
            List<Student> students = studentService.getAllStudents();
            CsvUtil.exportToCsv(students, "data/students_export.csv");
            exportStatusLabel.setText("Exported " + students.size() + " students to data/students_export.csv");
        } catch (IOException e) {
            exportStatusLabel.setText("Export failed: " + e.getMessage());
        }
    }
}
