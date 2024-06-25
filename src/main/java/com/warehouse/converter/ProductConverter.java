package com.warehouse.converter;

import com.warehouse.dto.ProductDto;
import com.warehouse.entity.Product;
import org.modelmapper.ModelMapper;

public class ProductConverter extends Converter<Product, ProductDto> {
    public ProductConverter() {
        super(new ModelMapper(), Product.class, ProductDto.class);
    }
}
