package com.warehouse.db;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {
    private final String url = "jdbc:mysql://localhost:3306/warehouse";
    private final String user = "root";
    private final String password = "zlagoda";
    private static DatabaseConnection instance;

    private final BasicConnectionPool connectionPool;

    public DatabaseConnection() throws SQLException {
        connectionPool = BasicConnectionPool.create(url, user, password);
    }

    public Connection getConnection() throws SQLException {
        return connectionPool.getConnection();
    }

    public BasicConnectionPool getConnectionPool() {
        return connectionPool;
    }

    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    public void shutdown() {
        try {
            connectionPool.shutdown();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
