package com.warehouse.dao.impl;

import com.warehouse.dao.UserDao;
import com.warehouse.db.DatabaseConnection;
import com.warehouse.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class UserDaoImpl implements UserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    private static final String FIND_BY_LOGIN = "SELECT * FROM users WHERE login = ?";

    private static final String INSERT_USER = "INSERT INTO users (login, password) VALUES (?, ?)";

    private Connection connection;
    public UserDaoImpl() throws SQLException {
        this(DatabaseConnection.getInstance().getConnection());
    }

    public UserDaoImpl(Connection connection) {
        this.connection = connection;
    }

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
        } finally {
            close();
        }
    }

    @Override
    public void saveUser(User user) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close();
        }
    }

    @Override
    public void close(){
        try {
            DatabaseConnection.getInstance().getConnectionPool().releaseConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
