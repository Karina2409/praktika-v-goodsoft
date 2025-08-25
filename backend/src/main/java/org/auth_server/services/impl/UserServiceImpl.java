package org.auth_server.services.impl;

import org.auth_server.dao.UserMapper;

import org.auth_server.entity.User;
import org.auth_server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userDao;

    @Override
    public List<User> findAllUsers() {
        return userDao.findAll();
    }

    @Override
    public User addUser(User user) {
        userDao.create(user);
        return userDao.findByLogin(user.getLogin());

    }

    @Override
    public void updateUser(User user) {
        userDao.update(user);
    }

    @Override
    public boolean deleteUser(String login) {
        if (login == null) {
            return false;
        }
        return userDao.delete(login);
    }

    @Override
    public User findUserByLogin(String login) {
        return userDao.findByLogin(login);
    }

    @Override
    public User findUserById(int id) {
        return userDao.findById(id);
    }

    @Override
    public User login(String login, String password) {
        return userDao.login(login, password);
    }
}
