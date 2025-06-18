package org.auth_server.services;

import org.auth_server.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User addUser(User user);
    void updateUser(User user);
    void deleteUser(String login);
    User getUserByLogin(String login);
    User login(String login, String password);
//    boolean changeUserPassword(String login, String oldPassword, String newPassword);
}
