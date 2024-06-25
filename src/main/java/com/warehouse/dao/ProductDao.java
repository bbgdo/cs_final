package com.warehouse.dao;

import com.warehouse.dao.GenericDao;
import com.warehouse.entity.Product;

public interface ProductDao extends GenericDao<Product, String> {
    void update(Product product, String name);
    void close();
}
