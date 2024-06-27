package com.warehouse.dao.impl;

import com.warehouse.db.DatabaseConnection;
import com.warehouse.entity.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductDaoImplTest {
    private DatabaseConnection dbConnection;
    private Connection connection;
    private ProductDaoImpl productDao;
    @BeforeEach
    void setUp() throws SQLException {
        dbConnection = DatabaseConnection.getInstance();
        connection = dbConnection.getConnection();
        productDao = new ProductDaoImpl(connection);
        // Add test product and category with known name
        connection.createStatement().execute("INSERT INTO categories (category_name, category_description) VALUES ('test_category_9999999999999', 'test_category_9999999999999')");
        connection.createStatement().execute("INSERT INTO products (product_name, product_description, product_producer, product_amount, product_price, product_category) " +
                "VALUES ('test_product_9999999999999', 'test_product_description', 'test_product_producer', 999, 999.99, 'test_category_9999999999999')");

    }

    @AfterEach
    void tearDown() throws SQLException {
        // Delete test product and category from db
        connection.createStatement().execute("DELETE FROM products WHERE product_name = 'test_product_9999999999999'");
        connection.createStatement().execute("DELETE FROM categories WHERE category_name = 'test_category_9999999999999'");

        productDao.close();
        dbConnection.shutdown();
    }

    @Test
    void testGetAllProductShouldReturnListWithElements() {
        List<Product> products = productDao.getAll();
        assertNotNull(products);
        assertFalse(products.isEmpty());
    }

    @Test
    void testCreateProductShouldIncreaseProductCount() {
        List<Product> initialProducts = productDao.getAll();
        Product product = Product.builder()
                .name("test_product_9999999999998")
                .description("test_product_9999999999998")
                .producer("test_product_9999999999998")
                .amount(99)
                .price(4.50)
                .category("test_category_9999999999999")
                .build();
        productDao.create(product);
        List<Product> updatedProducts = productDao.getAll();

        // Delete test products from db
        try {
            connection.createStatement().execute("DELETE FROM products WHERE product_name = 'test_product_9999999999998'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        assertEquals(initialProducts.size() + 1, updatedProducts.size());
    }

    @Test
    void testReadProductWithValidNameShouldReturnProduct() {
        Product product = productDao.getById("test_product_9999999999999").get();
        assertNotNull(product);
        assertEquals("test_product_9999999999999", product.getName());
        assertEquals("test_product_description", product.getDescription());
        assertEquals("test_product_producer", product.getProducer());
        assertEquals(999, product.getAmount());
        assertEquals(999.99, product.getPrice());
        assertEquals("test_category_9999999999999", product.getCategory());
    }

    @Test
    void testReadProductWithInvalidNameShouldReturnNull() {
        assertThrows(NoSuchElementException.class, () -> productDao.getById("test_product_8888888888888").get());
    }

    @Test
    void testUpdateProductWithValidIdShouldUpdateProductInDatabase() {
        Product productToUpdate = Product.builder()
                .name("test_product_9999999999998")
                .description("test_product_9999999999998")
                .producer("test_product_9999999999998")
                .amount(1234)
                .price(22.0)
                .category("test_category_9999999999999")
                .build();
        productDao.create(productToUpdate);
        Product updateProduct = Product.builder()
                .name("test_product_8888888888888")
                .description("test_product_8888888888888")
                .producer("test_product_8888888888888")
                .amount(498576)
                .price(33.0)
                .category("test_category_9999999999999")
                .build();
        productDao.update(updateProduct, "test_product_9999999999998");
        assertDoesNotThrow(() -> productDao.getById("test_product_8888888888888").get());
        Product updatedProduct = productDao.getById("test_product_8888888888888").get();
        // Delete test products from db
        try {
            connection.createStatement().execute("DELETE FROM products WHERE product_name = 'test_product_8888888888888'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        assertEquals("test_product_8888888888888", updatedProduct.getName());
        assertEquals("test_product_8888888888888", updatedProduct.getDescription());
        assertEquals("test_product_8888888888888", updatedProduct.getProducer());
        assertEquals(498576, updatedProduct.getAmount());
        assertEquals(33.0, updatedProduct.getPrice());
        assertEquals("test_category_9999999999999", updatedProduct.getCategory());
    }

    @Test
    void testDeleteProductWithValidIdShouldRemoveProductFromDatabase() {
        // Add test product to db
        try {
            connection.createStatement().execute("INSERT INTO products (product_name, product_description, product_producer, product_amount, product_price, product_category) " +
                    "VALUES ('test_product_8888888888888', 'test_product_8888888888888', 'test_product_8888888888888', 123, 123.99, 'test_category_9999999999999')");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        productDao.delete("test_product_8888888888888");
        assertThrows(NoSuchElementException.class, () -> productDao.getById("test_product_8888888888888").get());
    }

    @Test
    void testAddAmountProductShouldIncreaseProductAmount() {
        Product productBeforeAdd = productDao.getById("test_product_9999999999999").get();
        int addAmount = 1337;
        productDao.addAmount(addAmount, productBeforeAdd.getName());
        Product productAfterAdd = productDao.getById("test_product_9999999999999").get();
        assertEquals(productBeforeAdd.getAmount() + addAmount, productAfterAdd.getAmount());
    }

    @Test
    void testWriteOffProductShouldDecreaseProductAmount() {
        Product productBeforeWriteOff = productDao.getById("test_product_9999999999999").get();
        int writeOffAmount = productBeforeWriteOff.getAmount();
        productDao.writeOff(writeOffAmount, productBeforeWriteOff.getName());
        Product productAfterWriteOff = productDao.getById("test_product_9999999999999").get();
        assertEquals(productBeforeWriteOff.getAmount() - writeOffAmount, productAfterWriteOff.getAmount());
    }

    @Test
    void testWriteOffMoreThenProductAmountShouldThrowException() {
        Product productBeforeWriteOff = productDao.getById("test_product_9999999999999").get();
        int beforeWriteOffAmount = productBeforeWriteOff.getAmount();
        int writeOffAmount = productBeforeWriteOff.getAmount() + 1;
        assertThrows(IllegalArgumentException.class, () -> productDao.writeOff(writeOffAmount, productBeforeWriteOff.getName()));
        // Amount should not change
        assertEquals(beforeWriteOffAmount, productBeforeWriteOff.getAmount());
    }

    @Test
    void testConcurrentAddAmount() throws InterruptedException {
        final int numberOfThreads = 10;
        final int amountToAdd = 3;
        final CountDownLatch startLatch = new CountDownLatch(1);
        final CountDownLatch endLatch = new CountDownLatch(numberOfThreads);

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        Product initialProduct = productDao.getById("test_product_9999999999999").get();
        int initialAmount = initialProduct.getAmount();

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                try {
                    startLatch.await();
                    productDao.addAmount(amountToAdd, "test_product_9999999999999");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    endLatch.countDown();
                }
            });
        }

        startLatch.countDown(); // Start all threads
        endLatch.await(); // Wait for all threads to finish
        executorService.shutdown();

        Product updatedProduct = productDao.getById("test_product_9999999999999").get();
        int expectedAmount = initialAmount + (amountToAdd * numberOfThreads);

        assertEquals(expectedAmount, updatedProduct.getAmount());
    }

}