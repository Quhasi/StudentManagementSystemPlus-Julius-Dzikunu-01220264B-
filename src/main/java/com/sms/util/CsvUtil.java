package com.sms.util;

import com.sms.domain.Student;
import com.sms.domain.StudentStatus;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CsvUtil {

    public static void exportToCsv(List<Student> students, String filePath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("Student ID,Full Name,Programme,Level,GPA,Email,Phone,Date Added,Status");
            for (Student s : students) {
                writer.printf("%s,%s,%s,%d,%.2f,%s,%s,%s,%s%n",
                        s.getStudentId(), s.getFullName(), s.getProgramme(), s.getLevel(),
                        s.getGpa(), s.getEmail(), s.getPhoneNumber(), s.getDateAdded(), s.getStatus());
            }
        }
    }

    public static List<Student> importFromCsv(String filePath, List<String> errors) throws IOException {
        List<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine(); // Skip header
            int lineNum = 1;
            while ((line = reader.readLine()) != null) {
                lineNum++;
                try {
                    String[] parts = line.split(",");
                    if (parts.length < 7) throw new IllegalArgumentException("Insufficient columns");
                    
                    Student s = new Student();
                    s.setStudentId(parts[0].trim());
                    s.setFullName(parts[1].trim());
                    s.setProgramme(parts[2].trim());
                    s.setLevel(Integer.parseInt(parts[3].trim()));
                    s.setGpa(Double.parseDouble(parts[4].trim()));
                    s.setEmail(parts[5].trim());
                    s.setPhoneNumber(parts[6].trim());
                    
                    students.add(s);
                } catch (Exception e) {
                    errors.add("Line " + lineNum + ": " + e.getMessage());
                }
            }
        }
        return students;
    }
}
