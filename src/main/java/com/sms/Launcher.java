package com.sms;

/**
 * A wrapper class to launch the JavaFX application.
 * This bypasses the JavaFX runtime check that occurs when the main class extends Application.
 */
public class Launcher {
    public static void main(String[] args) {
        MainApp.main(args);
    }
}
