package com.hospital.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {
    private static final String DRIVER = getConfig("hospital.db.driver", "HOSPITAL_DB_DRIVER",
            "oracle.jdbc.OracleDriver");
    // 서비스명 형식 예비 URL: jdbc:oracle:thin:@//211.63.89.134:1521/orcl
    private static final String URL = getConfig("hospital.db.url", "HOSPITAL_DB_URL",
            "jdbc:oracle:thin:@211.63.89.134:1521:orcl");
    private static final String USER = getConfig("hospital.db.user", "HOSPITAL_DB_USER",
            "scott");
    private static final String PASSWORD = getConfig("hospital.db.password", "HOSPITAL_DB_PASSWORD",
            "tiger");

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("JDBC driver not found: " + DRIVER);
        }
    }

    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void close(AutoCloseable... resources) {
        if (resources == null) {
            return;
        }

        for (AutoCloseable resource : resources) {
            if (resource != null) {
                try {
                    resource.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

    private static String getConfig(String propertyName, String envName, String defaultValue) {
        String value = System.getProperty(propertyName);

        if (value == null || value.isBlank()) {
            value = System.getenv(envName);
        }

        if (value == null || value.isBlank()) {
            return defaultValue;
        }

        return value;
    }
}
