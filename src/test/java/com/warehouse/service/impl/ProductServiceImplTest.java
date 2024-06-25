package com.warehouse.service.impl;

import com.warehouse.converter.ProductConverter;
import com.warehouse.dao.ProductDao;
import com.warehouse.dto.ProductDto;
import com.warehouse.entity.Product;
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

class ProductServiceImplTest {
    @Mock
    private ProductDao productDao;

    @Mock
    private ProductConverter productConverter;

    @InjectMocks
    private ProductServiceImpl productService;

    private ProductDto testProductDto;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testProductDto = new ProductDto();
        testProductDto.setName("test_product_9999999999999");
        testProductDto.setDescription("test_product_description");
        testProductDto.setProducer("test_product_producer");
        testProductDto.setAmount(999);
        testProductDto.setPrice(999.99);
        testProductDto.setCategory("test_category_9999999999999");

        testProduct = new Product();
        testProduct.setName("test_product_9999999999999");
        testProduct.setDescription("test_product_description");
        testProduct.setProducer("test_product_producer");
        testProduct.setAmount(999);
        testProduct.setPrice(999.99);
        testProduct.setCategory("test_category_9999999999999");
    }

    @Test
    void testGetAllProductsShouldReturnListWithElements() {
        when(productDao.getAll()).thenReturn(Collections.singletonList(testProduct));
        when(productConverter.convertToDto(testProduct)).thenReturn(testProductDto);

        List<ProductDto> products = productService.getAll();
        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
        assertEquals(testProductDto, products.get(0));
    }

    @Test
    void testCreateProductShouldCallDaoCreate() {
        when(productConverter.convertToEntity(testProductDto)).thenReturn(testProduct);

        productService.create(testProductDto);

        verify(productDao, times(1)).create(testProduct);
    }

    @Test
    void testGetProductByIdWithValidNameShouldReturnProduct() {
        when(productDao.getById("test_product_9999999999999")).thenReturn(Optional.of(testProduct));
        when(productConverter.convertToDto(testProduct)).thenReturn(testProductDto);

        Optional<ProductDto> product = productService.getById("test_product_9999999999999");
        assertTrue(product.isPresent());
        assertEquals(testProductDto, product.get());
    }

    @Test
    void testGetProductByIdWithInvalidNameShouldReturnEmpty() {
        when(productDao.getById("test_product_8888888888888")).thenReturn(Optional.empty());

        Optional<ProductDto> product = productService.getById("test_product_8888888888888");
        assertFalse(product.isPresent());
    }

    @Test
    void testUpdateProductShouldCallDaoUpdate() {
        when(productConverter.convertToEntity(testProductDto)).thenReturn(testProduct);

        productService.update(testProductDto, "test_product_9999999999999");

        verify(productDao, times(1)).update(testProduct, "test_product_9999999999999");
    }

    @Test
    void testDeleteProductShouldCallDaoDelete() {
        productService.delete("test_product_9999999999999");

        verify(productDao, times(1)).delete("test_product_9999999999999");
    }
}
