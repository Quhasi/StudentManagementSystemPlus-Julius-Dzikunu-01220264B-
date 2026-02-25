package com.sms.service;

import com.sms.domain.Student;
import com.sms.repository.StudentRepository;
import com.sms.util.LoggerUtil;
import java.util.List;
import java.util.stream.Collectors;

public class StudentService {
    private final StudentRepository repository;
    private final ValidationService validationService;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
        this.validationService = new ValidationService();
    }

    public void addStudent(Student student) {
        validationService.validate(student);
        if (repository.findById(student.getStudentId()).isPresent()) {
            throw new IllegalArgumentException("Student ID already exists: " + student.getStudentId());
        }
        repository.add(student);
        LoggerUtil.log("Added student: " + student.getStudentId());
    }

    public void updateStudent(Student student) {
        validationService.validate(student);
        repository.update(student);
        LoggerUtil.log("Updated student: " + student.getStudentId());
    }

    public void deleteStudent(String studentId) {
        repository.delete(studentId);
        LoggerUtil.log("Deleted student: " + studentId);
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public List<Student> searchStudents(String query) {
        return repository.search(query);
    }

    public List<Student> filterStudents(String programme, Integer level, String status) {
        return repository.filter(programme, level, status);
    }

    // Reporting Logic
    public List<Student> getTopPerformers(int limit, String programme, Integer level) {
        return repository.findAll().stream()
                .filter(s -> (programme == null || s.getProgramme().equals(programme)))
                .filter(s -> (level == null || s.getLevel() == level))
                .sorted((s1, s2) -> Double.compare(s2.getGpa(), s1.getGpa()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<Student> getAtRiskStudents(double threshold) {
        return repository.findAll().stream()
                .filter(s -> s.getGpa() < threshold)
                .collect(Collectors.toList());
    }
}
