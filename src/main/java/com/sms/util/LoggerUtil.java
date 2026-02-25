package com.sms.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.io.File;

public class LoggerUtil {
    private static final String LOG_FILE = "data/app.log";

    static {
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
    }

    public static void log(String message) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             PrintWriter out = new PrintWriter(fw)) {
            out.println(LocalDateTime.now() + " - " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
