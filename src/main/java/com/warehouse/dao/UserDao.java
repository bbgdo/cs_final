package com.warehouse.dao;

import com.warehouse.entity.User;

public interface UserDao {
    User findByLogin(String login);
    void saveUser(User user);
    void close();
}
