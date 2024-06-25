package com.warehouse.service;

import com.warehouse.dto.CategoryDto;
public interface CategoryService extends GenericService<CategoryDto, String>{
    void update(CategoryDto categoryDto, String name);
}
