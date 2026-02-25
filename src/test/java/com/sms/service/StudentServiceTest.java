package com.sms.service;

import com.sms.domain.Student;
import com.sms.domain.StudentStatus;
import com.sms.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

class StudentServiceTest {
    private StudentService service;
    private MockRepository repository;

    @BeforeEach
    void setUp() {
        repository = new MockRepository();
        service = new StudentService(repository);
    }

    @Test
    void testAddValidStudent() {
        Student s = new Student("S1234", "John Doe", "CS", 100, 3.5, "john@example.com", "0123456789");
        assertDoesNotThrow(() -> service.addStudent(s));
    }

    @Test
    void testAddInvalidGpa() {
        Student s = new Student("S1234", "John Doe", "CS", 100, 5.0, "john@example.com", "0123456789");
        assertThrows(IllegalArgumentException.class, () -> service.addStudent(s));
    }

    @Test
    void testDuplicateId() {
        Student s1 = new Student("S1234", "John Doe", "CS", 100, 3.5, "john@example.com", "0123456789");
        service.addStudent(s1);
        Student s2 = new Student("S1234", "Jane Doe", "IT", 200, 3.8, "jane@example.com", "9876543210");
        assertThrows(IllegalArgumentException.class, () -> service.addStudent(s2));
    }

    // Mock Repository for testing
    private static class MockRepository implements StudentRepository {
        private List<Student> students = new ArrayList<>();
        @Override public void add(Student s) { students.add(s); }
        @Override public void update(Student s) {}
        @Override public void delete(String id) {}
        @Override public Optional<Student> findById(String id) {
            return students.stream().filter(s -> s.getStudentId().equals(id)).findFirst();
        }
        @Override public List<Student> findAll() { return students; }
        @Override public List<Student> search(String q) { return students; }
        @Override public List<Student> filter(String p, Integer l, String s) { return students; }
    }
}
