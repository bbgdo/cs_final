package com.warehouse.dao.impl;

import com.warehouse.dao.ProductDao;
import com.warehouse.db.DatabaseConnection;
import com.warehouse.entity.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDaoImpl implements ProductDao {
    private static final Logger LOGGER = LogManager.getLogger(ProductDaoImpl.class);

    private static final String GET_ALL = "SELECT * FROM products ORDER BY product_name";
    private static final String GET_BY_ID = "SELECT * FROM products WHERE product_name = ?";
    private static final String CREATE = "INSERT INTO products (product_name, product_description, product_producer, product_amount, product_price, product_category) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE products SET product_name = ?, product_description = ?, product_producer = ?, product_amount = ?, product_price = ?, product_category = ? WHERE product_name = ?";
    private static final String DELETE = "DELETE FROM products WHERE product_name = ?";

    private static final String NAME = "product_name";
    private static final String DESCRIPTION = "product_description";
    private static final String PRODUCER = "product_producer";
    private static final String AMOUNT = "product_amount";
    private static final String PRICE = "product_price";
    private static final String CATEGORY = "product_category";

    private Connection connection;
    private boolean connectionShouldBeClosed;

    public ProductDaoImpl() throws SQLException {
        this(DatabaseConnection.getInstance().getConnection(), true);
    }

    public ProductDaoImpl(Connection connection) {
        this(connection, false);
    }

    public ProductDaoImpl(Connection connection, boolean connectionShouldBeClosed) {
        this.connection = connection;
        this.connectionShouldBeClosed = connectionShouldBeClosed;
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();

        try (Statement query = connection.createStatement(); ResultSet resultSet = query.executeQuery(GET_ALL)) {
            while (resultSet.next()) {
                products.add(extractProductFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("ProductDaoImpl getAll SQL exception", e);
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public Optional<Product> getById(String name) {
        Optional<Product> product = Optional.empty();
        try (PreparedStatement query = connection.prepareStatement(GET_BY_ID)) {
            query.setString(1, name);
            ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                product = Optional.of(extractProductFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("ProductDaoImpl getById SQL exception: " + name, e);
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public void create(Product product) {
        try (PreparedStatement query = connection.prepareStatement(CREATE)) {
            query.setString(1, product.getName());
            query.setString(2, product.getDescription());
            query.setString(3, product.getProducer());
            query.setInt(4, product.getAmount());
            query.setDouble(5, product.getPrice());
            query.setString(6, product.getCategory());
            query.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("ProductDaoImpl create SQL exception", e);
            e.printStackTrace();
        }
    }

    @Override
    public void update(Product product, String name) {
        try (PreparedStatement query = connection.prepareStatement(UPDATE)) {
            query.setString(1, product.getName());
            query.setString(2, product.getDescription());
            query.setString(3, product.getProducer());
            query.setInt(4, product.getAmount());
            query.setDouble(5, product.getPrice());
            query.setString(6, product.getCategory());
            query.setString(7, name);
            query.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("ProductDaoImpl update SQL exception", e);
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String name) {
        try (PreparedStatement query = connection.prepareStatement(DELETE)) {
            query.setString(1, name);
            query.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("ProductDaoImpl delete SQL exception", e);
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            if (connectionShouldBeClosed && connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected static Product extractProductFromResultSet(ResultSet resultSet) throws SQLException {
        return Product.builder()
                .name(resultSet.getString(NAME))
                .description(resultSet.getString(DESCRIPTION))
                .producer(resultSet.getString(PRODUCER))
                .amount(resultSet.getInt(AMOUNT))
                .price(resultSet.getDouble(PRICE))
                .category(resultSet.getString(CATEGORY))
                .build();
    }
}
