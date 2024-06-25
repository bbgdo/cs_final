package com.warehouse.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface GenericDao<T, K> {

    List<T> getAll();

    Optional<T> getById(K id);

    void create(T e);

    void delete(K id);
}
