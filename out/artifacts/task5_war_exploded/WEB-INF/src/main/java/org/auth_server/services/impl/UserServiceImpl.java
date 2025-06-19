package org.auth_server.services.impl;

import org.auth_server.dao.UserDao;

import org.auth_server.entity.User;
import org.auth_server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public User addUser(User user) {
        if (getUserByLogin(user.getLogin()) == null) {
            userDao.create(user);
            return userDao.findByLogin(user.getLogin());
        }
        return null;
    }

    @Override
    public void updateUser(User user) {
        userDao.update(user);
    }

    @Override
    public void deleteUser(String login) {
        if (login != null) {
            userDao.delete(login);
        }
    }

    @Override
    public User getUserByLogin(String login) {
        return userDao.findByLogin(login);
    }

    @Override
    public User login(String login, String password) {
        return userDao.login(login, password);
    }

//    @Override
//    public boolean changeUserPassword(String login, String oldPassword, String newPassword) {
//        return userDao.changePassword(login, oldPassword, newPassword);
//    }
}
