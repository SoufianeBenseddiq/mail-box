package ma.ensa.mailbox.config;

import java.io.*;
import java.util.Properties;

public class EmailConfig {
    private static final Properties config = new Properties();
    private static final String CONFIG_FILE = "email.properties";

    static {
        loadConfig();
    }

    private static void loadConfig() {
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            config.load(input);
        } catch (IOException e) {
            System.out.println("Config file not found, using defaults");
        }
    }

    public static String getUsername() {
        return config.getProperty("email.username", "soufianebenseddiq8@gmail.com");
    }

    public static String getPassword() {
        return config.getProperty("email.password", "oydi zkuu segp ijwz");
    }

    public static String getSmtpHost() {
        return config.getProperty("smtp.host", "smtp.gmail.com");
    }

    public static String getSmtpPort() {
        return config.getProperty("smtp.port", "587");
    }
}