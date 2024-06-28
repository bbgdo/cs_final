package com.warehouse.converter;

import com.warehouse.dto.ProductDto;
import com.warehouse.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductConverterTest {
    private ProductConverter productConverter;

    @BeforeEach
    void setUp() {
        productConverter = new ProductConverter();
    }

    @Test
    void testConvertToEntity() {
        ProductDto productDto = ProductDto.builder()
                .name("test_name")
                .description("test_description")
                .producer("test_producer")
                .amount(1337)
                .price(13.37)
                .category("test_category")
                .build();

        Product product = productConverter.convertToEntity(productDto);

        assertNotNull(product);
        assertEquals(productDto.getName(), product.getName());
        assertEquals(productDto.getDescription(), product.getDescription());
        assertEquals(productDto.getProducer(), product.getProducer());
        assertEquals(productDto.getAmount(), product.getAmount());
        assertEquals(productDto.getPrice(), product.getPrice());
        assertEquals(productDto.getCategory(), product.getCategory());
    }

    @Test
    void testConvertToDto() {
        Product product = Product.builder()
                .name("test_name")
                .description("test_description")
                .producer("test_producer")
                .amount(1337)
                .price(13.37)
                .category("test_category")
                .build();

        ProductDto productDto = productConverter.convertToDto(product);

        assertNotNull(productDto);
        assertEquals(product.getName(), productDto.getName());
        assertEquals(product.getDescription(), productDto.getDescription());
        assertEquals(product.getProducer(), productDto.getProducer());
        assertEquals(product.getAmount(), productDto.getAmount());
        assertEquals(product.getPrice(), productDto.getPrice());
        assertEquals(product.getCategory(), productDto.getCategory());
    }
}