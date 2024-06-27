package com.warehouse.dao.impl;

import com.warehouse.dao.UserDao;
import com.warehouse.db.DatabaseConnection;
import com.warehouse.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class UserDaoImpl implements UserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    String FIND_BY_LOGIN = "SELECT * FROM users WHERE login = ?";

    public UserDaoImpl() throws SQLException {
        this(DatabaseConnection.getInstance().getConnection(), true);
    }

    public UserDaoImpl(Connection connection) {
        this(connection, false);
    }

    public UserDaoImpl(Connection connection, boolean connectionShouldBeClosed) {
        this.connection = connection;
        this.connectionShouldBeClosed = connectionShouldBeClosed;
    }

    private Connection connection;
    private boolean connectionShouldBeClosed;

    @Override
    public User findByLogin(String login) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
