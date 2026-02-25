package com.sms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:data/students.db";

    static {
        // Ensure data directory exists
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        initializeDatabase();
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    private static void initializeDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS students (" +
                "student_id TEXT PRIMARY KEY," +
                "full_name TEXT NOT NULL," +
                "programme TEXT NOT NULL," +
                "level INTEGER NOT NULL CHECK (level IN (100, 200, 300, 400, 500, 600, 700))," +
                "gpa REAL NOT NULL CHECK (gpa >= 0.0 AND gpa <= 4.0)," +
                "email TEXT NOT NULL," +
                "phone_number TEXT NOT NULL," +
                "date_added TEXT NOT NULL," +
                "status TEXT NOT NULL CHECK (status IN ('ACTIVE', 'INACTIVE'))" +
                ");";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
