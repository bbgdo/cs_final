package com.warehouse.db;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {
    private DatabaseConnection databaseConnection;

    @BeforeEach
    void setUp() throws SQLException {
        databaseConnection = DatabaseConnection.getInstance();
    }

    @AfterEach
    void tearDown() {
        databaseConnection.shutdown();
    }

    @Test
    void testGetConnection() throws SQLException {
        Connection connection = databaseConnection.getConnection();
        assertNotNull(connection);
        assertTrue(connection.isValid(1));
    }

    @Test
    void testGetInstance() throws SQLException {
        DatabaseConnection anotherInstance = DatabaseConnection.getInstance();
        assertSame(databaseConnection, anotherInstance);
    }

    @Test
    void testConnectionPool() {
        BasicConnectionPool connectionPool = databaseConnection.getConnectionPool();
        assertNotNull(connectionPool);
        assertEquals(10, connectionPool.getSize());
    }
}