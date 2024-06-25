package com.warehouse.dao;

import com.warehouse.entity.Category;

import java.sql.SQLException;

public interface CategoryDao extends GenericDao<Category, String> {
    void update(Category category, String name);
    void close();
}
