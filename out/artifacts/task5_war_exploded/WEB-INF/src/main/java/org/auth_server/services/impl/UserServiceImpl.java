package org.auth_server.services.impl;

import org.auth_server.dao.UserDao;
import org.auth_server.dao.impl.InMemoryStorage;
import org.auth_server.entity.User;
import org.auth_server.services.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private static UserServiceImpl instance;

    private final UserDao userDao = InMemoryStorage.getInstance();

    private UserServiceImpl() {}

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAll();
    }

    @Override
    public User addUser(User user) {
        if (getUserByLogin(user.getLogin()) == null) {
            userDao.create(user);
            return user;
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
        return userDao.getByLogin(login);
    }

    @Override
    public User login(String login, String password) {
        return userDao.login(login, password);
    }

    @Override
    public boolean changeUserPassword(String login, String oldPassword, String newPassword) {
        return userDao.changePassword(login, oldPassword, newPassword);
    }
}
