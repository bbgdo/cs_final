package com.warehouse.converter;

import com.warehouse.dto.CategoryDto;
import com.warehouse.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryConverterTest {
    private CategoryConverter categoryConverter;

    @BeforeEach
    void setUp() {
        categoryConverter = new CategoryConverter();
    }

    @Test
    void testConvertToEntity() {
        CategoryDto categoryDto = CategoryDto.builder()
                .name("test_name")
                .description("test_description")
                .build();

        Category category = categoryConverter.convertToEntity(categoryDto);

        assertNotNull(category);
        assertEquals(categoryDto.getName(), category.getName());
        assertEquals(categoryDto.getDescription(), category.getDescription());
    }

    @Test
    void testConvertToDto() {
        Category category = Category.builder()
                .name("test_name")
                .description("test_description")
                .build();

        CategoryDto categoryDto = categoryConverter.convertToDto(category);

        assertNotNull(categoryDto);
        assertEquals(category.getName(), categoryDto.getName());
        assertEquals(category.getDescription(), categoryDto.getDescription());
    }
}