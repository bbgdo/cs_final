package com.warehouse.converter;

import com.warehouse.dto.CategoryDto;
import com.warehouse.entity.Category;
import org.modelmapper.ModelMapper;

public class CategoryConverter extends Converter<Category, CategoryDto> {
    public CategoryConverter() {
        super(new ModelMapper(), Category.class, CategoryDto.class);
    }
}
