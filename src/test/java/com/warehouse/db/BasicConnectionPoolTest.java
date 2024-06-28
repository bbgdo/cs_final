package com.warehouse.db;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class BasicConnectionPoolTest {
    private BasicConnectionPool connectionPool;
    private final String url = "jdbc:mysql://localhost:3306/warehouse";
    private final String user = "root";
    private final String password = "zlagoda";

    @BeforeEach
    void setUp() throws SQLException {
        connectionPool = BasicConnectionPool.create(url, user, password);
    }

    @AfterEach
    void tearDown() throws SQLException {
        connectionPool.shutdown();
    }

    @Test
    void testGetConnection() throws SQLException {
        Connection connection = connectionPool.getConnection();
        assertNotNull(connection);
        assertTrue(connection.isValid(1));
        connectionPool.releaseConnection(connection);
    }

    @Test
    void testReleaseConnection() throws SQLException {
        Connection connection = connectionPool.getConnection();
        assertTrue(connectionPool.releaseConnection(connection));
        assertEquals(10, connectionPool.getSize());
    }

    @Test
    void testGetConnectionWhenPoolIsEmpty() throws SQLException {
        Connection connectionToRealese = connectionPool.getConnection();
        for (int i = 0; i < 19; i++) {
            connectionPool.getConnection();
        }

        // No available connections now
        assertThrows(RuntimeException.class, () -> connectionPool.getConnection());

        // After releaseConnection connection can be obtained
        connectionPool.releaseConnection(connectionToRealese);
        assertNotNull(connectionPool.getConnection());
    }

    @Test
    void testPoolExpansion() throws SQLException {
        // Initial size
        for (int i = 0; i < 10; i++) {
            connectionPool.getConnection();
        }
        // Extra size
        for (int i = 0; i < 10; i++) {
            connectionPool.getConnection();
        }

        assertEquals(20, connectionPool.getSize());
    }
}