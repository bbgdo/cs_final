package com.warehouse.service.impl;

import com.warehouse.converter.ProductConverter;
import com.warehouse.dao.ProductDao;
import com.warehouse.dto.ProductDto;
import com.warehouse.service.ProductService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductServiceImpl implements ProductService {
    private final ProductDao productDao;
    private final ProductConverter productConverter;

    public ProductServiceImpl(ProductDao productDao, ProductConverter productConverter) {
        this.productDao = productDao;
        this.productConverter = productConverter;
    }

    @Override
    public List<ProductDto> getAll() {
        return productDao.getAll().stream()
                .map(productConverter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDto> getById(String name) {
        return productDao.getById(name)
                .map(productConverter::convertToDto);
    }

    @Override
    public void create(ProductDto productDto) {
        productDao.create(productConverter.convertToEntity(productDto));
    }

    @Override
    public void update(ProductDto productDto, String name){
        productDao.update(productConverter.convertToEntity(productDto), name);
    }

    @Override
    public void delete(String name) {
        productDao.delete(name);
    }
}
