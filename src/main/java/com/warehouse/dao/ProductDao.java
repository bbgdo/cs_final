package com.warehouse.dao;

import com.warehouse.entity.Product;

import java.util.List;

public interface ProductDao extends GenericDao<Product, String> {
    void addAmount(int amount, String name);
    void writeOff(int amount, String name);
    List<Product> findByCategory(String name);
    void update(Product product, String name);
    void close();
}
