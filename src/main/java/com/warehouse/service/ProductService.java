package com.warehouse.service;

import com.warehouse.dto.ProductDto;

import java.util.List;

public interface ProductService extends GenericService <ProductDto, String> {
    void addAmount(int amount, String name);
    void writeOff(int amount, String name);
    List<ProductDto> findByCategory(String name);
    void update(ProductDto productDto, String name);
}
