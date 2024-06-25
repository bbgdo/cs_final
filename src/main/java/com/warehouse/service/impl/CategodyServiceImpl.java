package com.warehouse.service.impl;

import com.warehouse.converter.CategoryConverter;
import com.warehouse.dao.CategoryDao;
import com.warehouse.dto.CategoryDto;
import com.warehouse.service.CategoryService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CategodyServiceImpl implements CategoryService {
    private final CategoryDao categoryDao;
    private final CategoryConverter categoryConverter;

    public CategodyServiceImpl(CategoryDao categoryDao, CategoryConverter categoryConverter) {
        this.categoryDao = categoryDao;
        this.categoryConverter = categoryConverter;
    }

    @Override
    public List<CategoryDto> getAll() {
        return categoryDao.getAll().stream()
                .map(categoryConverter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CategoryDto> getById(String name) {
        return categoryDao.getById(name)
                .map(categoryConverter::convertToDto);
    }

    @Override
    public void create(CategoryDto categoryDto) {
        categoryDao.create(categoryConverter.convertToEntity(categoryDto));
    }

    @Override
    public void update(CategoryDto categoryDto, String name) {
        categoryDao.update(categoryConverter.convertToEntity(categoryDto), name);
    }

    @Override
    public void delete(String name) {
        categoryDao.delete(name);
    }
}
