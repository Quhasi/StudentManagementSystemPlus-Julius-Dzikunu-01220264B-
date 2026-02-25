package com.sms.repository;

import com.sms.domain.Student;
import com.sms.domain.StudentStatus;
import com.sms.util.DatabaseManager;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SQLiteStudentRepository implements StudentRepository {

    @Override
    public void add(Student student) {
        String sql = "INSERT INTO students(student_id, full_name, programme, level, gpa, email, phone_number, date_added, status) VALUES(?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            setStudentParams(pstmt, student);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding student", e);
        }
    }

    @Override
    public void update(Student student) {
        String sql = "UPDATE students SET full_name=?, programme=?, level=?, gpa=?, email=?, phone_number=?, date_added=?, status=? WHERE student_id=?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getFullName());
            pstmt.setString(2, student.getProgramme());
            pstmt.setInt(3, student.getLevel());
            pstmt.setDouble(4, student.getGpa());
            pstmt.setString(5, student.getEmail());
            pstmt.setString(6, student.getPhoneNumber());
            pstmt.setString(7, student.getDateAdded().toString());
            pstmt.setString(8, student.getStatus().name());
            pstmt.setString(9, student.getStudentId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating student", e);
        }
    }

    @Override
    public void delete(String studentId) {
        String sql = "DELETE FROM students WHERE student_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting student", e);
        }
    }

    @Override
    public Optional<Student> findById(String studentId) {
        String sql = "SELECT * FROM students WHERE student_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapResultSetToStudent(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding student", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                students.add(mapResultSetToStudent(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all students", e);
        }
        return students;
    }

    @Override
    public List<Student> search(String query) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE student_id LIKE ? OR full_name LIKE ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String searchTerm = "%" + query + "%";
            pstmt.setString(1, searchTerm);
            pstmt.setString(2, searchTerm);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                students.add(mapResultSetToStudent(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error searching students", e);
        }
        return students;
    }

    @Override
    public List<Student> filter(String programme, Integer level, String status) {
        List<Student> students = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM students WHERE 1=1");
        if (programme != null && !programme.isEmpty()) sql.append(" AND programme = ?");
        if (level != null) sql.append(" AND level = ?");
        if (status != null && !status.isEmpty()) sql.append(" AND status = ?");

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (programme != null && !programme.isEmpty()) pstmt.setString(paramIndex++, programme);
            if (level != null) pstmt.setInt(paramIndex++, level);
            if (status != null && !status.isEmpty()) pstmt.setString(paramIndex++, status);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                students.add(mapResultSetToStudent(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error filtering students", e);
        }
        return students;
    }

    private void setStudentParams(PreparedStatement pstmt, Student student) throws SQLException {
        pstmt.setString(1, student.getStudentId());
        pstmt.setString(2, student.getFullName());
        pstmt.setString(3, student.getProgramme());
        pstmt.setInt(4, student.getLevel());
        pstmt.setDouble(5, student.getGpa());
        pstmt.setString(6, student.getEmail());
        pstmt.setString(7, student.getPhoneNumber());
        pstmt.setString(8, student.getDateAdded().toString());
        pstmt.setString(9, student.getStatus().name());
    }

    private Student mapResultSetToStudent(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setStudentId(rs.getString("student_id"));
        student.setFullName(rs.getString("full_name"));
        student.setProgramme(rs.getString("programme"));
        student.setLevel(rs.getInt("level"));
        student.setGpa(rs.getDouble("gpa"));
        student.setEmail(rs.getString("email"));
        student.setPhoneNumber(rs.getString("phone_number"));
        student.setDateAdded(LocalDate.parse(rs.getString("date_added")));
        student.setStatus(StudentStatus.valueOf(rs.getString("status")));
        return student;
    }
}
