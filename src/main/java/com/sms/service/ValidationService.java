package com.sms.service;

import com.sms.domain.Student;
import java.util.Arrays;
import java.util.List;

public class ValidationService {
    private static final List<Integer> VALID_LEVELS = Arrays.asList(100, 200, 300, 400, 500, 600, 700);

    public void validate(Student student) {
        if (student.getStudentId() == null || !student.getStudentId().matches("^[a-zA-Z0-9]{4,20}$")) {
            throw new IllegalArgumentException("Student ID must be 4-20 alphanumeric characters.");
        }
        if (student.getFullName() == null || student.getFullName().length() < 2 || student.getFullName().length() > 60 || student.getFullName().matches(".*\\d.*")) {
            throw new IllegalArgumentException("Full name must be 2-60 characters and contain no digits.");
        }
        if (student.getProgramme() == null || student.getProgramme().isEmpty()) {
            throw new IllegalArgumentException("Programme is required.");
        }
        if (!VALID_LEVELS.contains(student.getLevel())) {
            throw new IllegalArgumentException("Invalid level. Must be 100-700.");
        }
        if (student.getGpa() < 0.0 || student.getGpa() > 4.0) {
            throw new IllegalArgumentException("GPA must be between 0.0 and 4.0.");
        }
        if (student.getEmail() == null || !student.getEmail().contains("@") || !student.getEmail().contains(".")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        if (student.getPhoneNumber() == null || !student.getPhoneNumber().matches("^\\d{10,15}$")) {
            throw new IllegalArgumentException("Phone number must be 10-15 digits.");
        }
    }
}
