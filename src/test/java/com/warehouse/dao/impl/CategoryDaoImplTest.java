package com.warehouse.dao.impl;

import com.warehouse.db.DatabaseConnection;
import com.warehouse.entity.Category;
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

public class CategoryDaoImplTest {
    private DatabaseConnection dbConnection;
    private Connection connection;
    private CategoryDaoImpl categoryDao;

    @BeforeEach
    void setUp() throws SQLException {
        dbConnection = DatabaseConnection.getInstance();
        connection = dbConnection.getConnection();
        categoryDao = new CategoryDaoImpl(connection);

        // Add test category with known name
        connection.createStatement().execute("INSERT INTO categories (category_name, category_description) " +
                "VALUES ('test_category_9999999999999', 'test_category_9999999999999')");
    }

    @AfterEach
    void tearDown() throws SQLException {
        // Delete the test category from db
        connection.createStatement().execute("DELETE FROM categories WHERE category_name = 'test_category_9999999999999'");

        categoryDao.close();
        dbConnection.shutdown();
    }

    @Test
    void testGetAllCategoriesShouldReturnListWithElements() {
        List<Category> categories = categoryDao.getAll();
        assertNotNull(categories);
        assertFalse(categories.isEmpty());
    }

    @Test
    void testCreateCategoryShouldIncreaseCategoryCount() {
        List<Category> initialCategories = categoryDao.getAll();
        Category category = Category.builder()
                .name("test_category_9999999999998")
                .description("test_category_9999999999998")
                .build();
        categoryDao.create(category);
        List<Category> updatedCategories = categoryDao.getAll();

        // Delete the test category from db
        try {
            connection.createStatement().execute("DELETE FROM categories WHERE category_name = 'test_category_9999999999998'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        assertEquals(initialCategories.size() + 1, updatedCategories.size());
    }

    @Test
    void testReadCategoryWithValidNameShouldReturnCategory() {
        Category category = categoryDao.getById("test_category_9999999999999").get();
        assertNotNull(category);
        assertEquals("test_category_9999999999999", category.getName());
        assertEquals("test_category_9999999999999", category.getDescription());
    }

    @Test
    void testReadCategoryWithInvalidNameShouldReturnNull() {
        assertThrows(NoSuchElementException.class, () -> categoryDao.getById("test_category_8888888888888").get());
    }

    @Test
    void testUpdateCategoryWithValidNameShouldUpdateCategoryInDatabase() {
        Category categoryToUpdate = Category.builder()
                .name("test_category_9999999999998")
                .description("test_category_9999999999998")
                .build();
        categoryDao.create(categoryToUpdate);
        Category updateCategory = Category.builder()
                .name("test_category_8888888888888")
                .description("test_category_8888888888888")
                .build();
        categoryDao.update(updateCategory, "test_category_9999999999998");
        assertDoesNotThrow(() -> categoryDao.getById("test_category_8888888888888").get());
        Category updatedCategory = categoryDao.getById("test_category_8888888888888").get();
        // Delete the test category from db
        try {
            connection.createStatement().execute("DELETE FROM categories WHERE category_name = 'test_category_8888888888888'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        assertEquals("test_category_8888888888888", updatedCategory.getName());
        assertEquals("test_category_8888888888888", updatedCategory.getDescription());
    }

    @Test
    void testDeleteCategoryWithValidNameShouldRemoveCategoryFromDatabase() {
        // Add a test category to db
        try {
            connection.createStatement().execute("INSERT INTO categories (category_name, category_description) " +
                    "VALUES ('test_category_8888888888888', 'test_category_8888888888888')");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        categoryDao.delete("test_category_8888888888888");
        assertThrows(NoSuchElementException.class, () -> categoryDao.getById("test_category_8888888888888").get());
    }

    @Test
    void testSimultaneousAccessShouldHandleConcurrency() throws InterruptedException, ExecutionException {
        int numberOfThreads = 150;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

        Callable<Optional<Category>> task = () -> {
            CategoryDaoImpl localDao = new CategoryDaoImpl();
            return localDao.getById("test_category_9999999999999");
        };

        List<Future<Optional<Category>>> futures = new ArrayList<>();
        for (int i = 0; i < numberOfThreads; i++) {
            futures.add(executor.submit(task));
        }

        for (Future<Optional<Category>> future : futures) {
            Optional<Category> category = future.get();
            assertTrue(category.isPresent());
            assertEquals("test_category_9999999999999", category.get().getName());
        }

        executor.shutdown();
    }

}
