package com.warehouse.dao.impl;

import com.warehouse.db.DatabaseConnection;
import com.warehouse.entity.Product;
import com.warehouse.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDaoImplTest {
    private DatabaseConnection dbConnection;
    private Connection connection;
    private UserDaoImpl userDao;

    @BeforeEach
    void setUp() throws SQLException {
        dbConnection = DatabaseConnection.getInstance();
        connection = dbConnection.getConnection();
        userDao = new UserDaoImpl(connection);

        // Add test user with known login
        connection.createStatement().execute("INSERT INTO users (login, password) VALUES ('test_user_9999999999999', 'password')");
    }

    @AfterEach
    void tearDown() throws SQLException {
        // Delete test user from db
        connection.createStatement().execute("DELETE FROM users WHERE login = 'test_user_9999999999999'");

        userDao.close();
        dbConnection.shutdown();
    }

    @Test
    void testFindByLoginShouldReturnUser() {
        User user = userDao.findByLogin("test_user_9999999999999");
        assertNotNull(user);
        assertEquals("test_user_9999999999999", user.getLogin());
        assertEquals("password", user.getPassword());
    }

    @Test
    void testFindByLoginWithInvalidLoginShouldReturnNull() {
        assertNull(userDao.findByLogin("invalid_login"));
    }

    @Test
    void testSaveUserShouldIncreaseUserCount() throws SQLException {
        User user = new User();
        user.setLogin("test_user_9999999999998");
        user.setPassword("password");

        userDao.saveUser(user);

        User retrievedUser = userDao.findByLogin("test_user_9999999999998");

        // Delete test user from db
        connection.createStatement().execute("DELETE FROM users WHERE login = 'test_user_9999999999998'");

        assertNotNull(retrievedUser);
        assertEquals("test_user_9999999999998", retrievedUser.getLogin());
        assertEquals("password", retrievedUser.getPassword());
    }

    @Test
    void testSimultaneousMoreConnectionsThanInConnectionPool() throws InterruptedException, ExecutionException {
        int numberOfThreads = 150;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

        Callable<Optional<User>> task = () -> {
            UserDaoImpl localDao = new UserDaoImpl();
            User user = localDao.findByLogin("test_user_9999999999999");
            return Optional.ofNullable(user);
        };

        List<Future<Optional<User>>> futures = new ArrayList<>();
        for (int i = 0; i < numberOfThreads; i++) {
            futures.add(executor.submit(task));
        }

        for (Future<Optional<User>> future : futures) {
            Optional<User> user = future.get();
            assertTrue(user.isPresent());
            assertEquals("test_user_9999999999999", user.get().getLogin());
        }

        executor.shutdown();
    }

}