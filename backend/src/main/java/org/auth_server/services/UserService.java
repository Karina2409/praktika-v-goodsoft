package org.auth_server.services;

import org.auth_server.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAllUsers();
    User addUser(User user);
    void updateUser(User user);
    boolean deleteUser(String login);
    User findUserByLogin(String login);
    User findUserById(int id);
    User login(String login, String password);
}
