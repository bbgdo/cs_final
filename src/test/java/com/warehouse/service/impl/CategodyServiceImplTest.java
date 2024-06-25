package com.warehouse.service.impl;

import com.warehouse.converter.CategoryConverter;
import com.warehouse.dao.CategoryDao;
import com.warehouse.dto.CategoryDto;
import com.warehouse.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {
    @Mock
    private CategoryDao categoryDao;

    @Mock
    private CategoryConverter categoryConverter;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private CategoryDto testCategoryDto;
    private Category testCategory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testCategoryDto = new CategoryDto();
        testCategoryDto.setName("test_category_9999999999999");
        testCategoryDto.setDescription("test_category_9999999999999");

        testCategory = new Category();
        testCategory.setName("test_category_9999999999999");
        testCategory.setDescription("test_category_9999999999999");
    }

    @Test
    void testGetAllCategoriesShouldReturnListWithElements() {
        when(categoryDao.getAll()).thenReturn(Collections.singletonList(testCategory));
        when(categoryConverter.convertToDto(testCategory)).thenReturn(testCategoryDto);

        List<CategoryDto> categories = categoryService.getAll();
        assertNotNull(categories);
        assertFalse(categories.isEmpty());
        assertEquals(1, categories.size());
        assertEquals(testCategoryDto, categories.get(0));
    }

    @Test
    void testCreateCategoryShouldCallDaoCreate() {
        when(categoryConverter.convertToEntity(testCategoryDto)).thenReturn(testCategory);

        categoryService.create(testCategoryDto);

        verify(categoryDao, times(1)).create(testCategory);
    }

    @Test
    void testGetCategoryByIdWithValidNameShouldReturnCategory() {
        when(categoryDao.getById("test_category_9999999999999")).thenReturn(Optional.of(testCategory));
        when(categoryConverter.convertToDto(testCategory)).thenReturn(testCategoryDto);

        Optional<CategoryDto> category = categoryService.getById("test_category_9999999999999");
        assertTrue(category.isPresent());
        assertEquals(testCategoryDto, category.get());
    }

    @Test
    void testGetCategoryByIdWithInvalidNameShouldReturnEmpty() {
        when(categoryDao.getById("test_category_8888888888888")).thenReturn(Optional.empty());

        Optional<CategoryDto> category = categoryService.getById("test_category_8888888888888");
        assertFalse(category.isPresent());
    }

    @Test
    void testUpdateCategoryShouldCallDaoUpdate() {
        when(categoryConverter.convertToEntity(testCategoryDto)).thenReturn(testCategory);

        categoryService.update(testCategoryDto, "test_category_9999999999999");

        verify(categoryDao, times(1)).update(testCategory, "test_category_9999999999999");
    }

    @Test
    void testDeleteCategoryShouldCallDaoDelete() {
        categoryService.delete("test_category_9999999999999");

        verify(categoryDao, times(1)).delete("test_category_9999999999999");
    }
}
