package com.warehouse.dao;

import com.warehouse.dto.CategoryDto;
import com.warehouse.entity.Category;

import java.sql.SQLException;

public interface CategoryDao extends GenericDao<Category, String> {
    double categoryValue(String name);
    void update(Category category, String name);
    void close();
}
