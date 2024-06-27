package com.warehouse.dao.impl;

import com.warehouse.dao.CategoryDao;
import com.warehouse.db.DatabaseConnection;
import com.warehouse.entity.Category;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryDaoImpl implements CategoryDao {
    private static final Logger LOGGER = LogManager.getLogger(CategoryDaoImpl.class);

    private static String GET_ALL = "SELECT * FROM categories ORDER BY category_name";
    private static String GET_BY_ID = "SELECT * FROM categories WHERE category_name = ?";
    private static String GET_VALUE = "SELECT SUM(product_amount * product_price) AS category_value FROM products WHERE product_category = ?";
    private static String CREATE = "INSERT INTO categories (category_name, category_description) VALUES (?, ?)";
    private static String UPDATE = "UPDATE categories SET category_name = ?, category_description = ? WHERE category_name = ?";
    private static String DELETE = "DELETE FROM categories WHERE category_name = ?";

    private static String NAME = "category_name";
    private static String DESCRIPTION = "category_description";

    private Connection connection;
    private boolean connectionShouldBeClosed;

    public CategoryDaoImpl() throws SQLException {
        this(DatabaseConnection.getInstance().getConnection(), true);
    }

    public CategoryDaoImpl(Connection connection) {
        this(connection, false);
    }

    public CategoryDaoImpl(Connection connection, boolean connectionShouldBeClosed) {
        this.connection = connection;
        this.connectionShouldBeClosed = connectionShouldBeClosed;
    }

    @Override
    public List<Category> getAll(){
        List<Category> categories = new ArrayList<>();

        try (Statement query = connection.createStatement(); ResultSet resultSet = query.executeQuery(GET_ALL)) {
            while (resultSet.next()) {
                categories.add(extractCategoryFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("CategoryDaoImpl getAll SQL exception", e);
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public Optional<Category> getById(String name){
        Optional<Category> category = Optional.empty();
        try (PreparedStatement query = connection.prepareStatement(GET_BY_ID)) {
            query.setString(1, name);
            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                category = Optional.of(extractCategoryFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("CategoryDaoImpl getById SQL exception: " + name, e);
            e.printStackTrace();
        }
        return category;
    }

    @Override
    public double categoryValue(String name) {
        double value = 0;
        try (PreparedStatement query = connection.prepareStatement(GET_VALUE)) {
            query.setString(1, name);
            ResultSet resultSet = query.executeQuery();
            if (resultSet.next()) {
                value = resultSet.getDouble("category_value");
            }
        } catch (SQLException e) {
            LOGGER.error("CategoryDaoImpl categoryValue SQL exception: " + name, e);
            e.printStackTrace();
        }
        return value;
    }

    @Override
    public void create(Category category){
        try (PreparedStatement query = connection.prepareStatement(CREATE)) {
            query.setString(1, category.getName());
            query.setString(2, category.getDescription());
            query.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("CategoryDaoImpl create SQL exception", e);
            e.printStackTrace();
        }
    }

    @Override
    public void update(Category category, String newName){
        try (PreparedStatement query = connection.prepareStatement(UPDATE)) {
            query.setString(1, category.getName());
            query.setString(2, category.getDescription());
            query.setString(3, newName);
            query.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("CategoryDaoImpl update SQL exception", e);
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String name){
        try (PreparedStatement query = connection.prepareStatement(DELETE)) {
            query.setString(1, name);
            query.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("CategoryDaoImpl delete SQL exception", e);
            e.printStackTrace();
        }
    }

    @Override
    public void close(){
        try {
            if (connectionShouldBeClosed && connection != null && !connection.isClosed()) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected static Category extractCategoryFromResultSet(ResultSet resultSet) throws SQLException {
        return Category.builder()
                .name(resultSet.getString(NAME))
                .description(resultSet.getString(DESCRIPTION))
                .build();
    }
}
