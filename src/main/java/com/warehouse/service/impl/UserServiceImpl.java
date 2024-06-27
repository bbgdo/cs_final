package com.warehouse.service.impl;

import com.warehouse.dao.UserDao;
import com.warehouse.entity.User;
import com.warehouse.service.UserService;
import com.warehouse.util.PasswordUtil;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean validateUser(String login, String password) {
        User user = userDao.findByLogin(login);
        return user != null && user.getPassword().equals(PasswordUtil.hashPassword(password));
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(PasswordUtil.hashPassword(user.getPassword()));
        userDao.saveUser(user);
    }
}
