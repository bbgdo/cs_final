package com.warehouse.service;

import com.warehouse.dto.ProductDto;

public interface ProductService extends GenericService <ProductDto, String> {
    void addAmount(int amount, String name);
    void writeOff(int amount, String name);
    void update(ProductDto productDto, String name);
}
