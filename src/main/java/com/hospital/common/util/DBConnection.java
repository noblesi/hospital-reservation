package com.hospital.common.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public final class DBConnection {
    private static final String JNDI_NAME = "java:comp/env/jdbc/hospitalDB";
    private static volatile DataSource dataSource;

    /**
     * 유틸리티 클래스의 인스턴스 생성을 막는 생성자입니다.
     */
    private DBConnection() {
    }

    /**
     * Tomcat DBCP 커넥션 풀에서 Connection을 가져옵니다.
     */
    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    /**
     * 전달받은 JDBC 자원을 순서대로 닫습니다.
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

    private static DataSource getDataSource() throws SQLException {
        if (dataSource == null) {
            synchronized (DBConnection.class) {
                if (dataSource == null) {
                    dataSource = lookupDataSource();
                }
            }
        }

        return dataSource;
    }

    private static DataSource lookupDataSource() throws SQLException {
        try {
            Context context = new InitialContext();
            return (DataSource) context.lookup(JNDI_NAME);
        } catch (NamingException e) {
            throw new SQLException("JNDI DataSource lookup failed: " + JNDI_NAME, e);
        }
    }
}
