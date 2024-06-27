package com.warehouse.service;

import com.warehouse.entity.User;

public interface UserService {
    boolean validateUser(String login, String password);

    void saveUser(User user);

}