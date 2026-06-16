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

    /**
     * 유틸리티 클래스의 인스턴스 생성을 막는 생성자입니다.
     */
    private DBConnection() {
    }

    /**
     * 설정된 DB 접속 정보로 Connection을 생성하는 메서드입니다.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * 전달받은 JDBC 자원을 순서대로 닫는 메서드입니다.
     */
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

    /**
     * 시스템 속성, 환경 변수, 기본값 순서로 설정 값을 가져오는 메서드입니다.
     */
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
