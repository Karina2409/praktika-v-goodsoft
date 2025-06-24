package org.auth_server.dao;

import org.auth_server.entity.User;

import java.util.List;

public interface UserDao {
    List<User> findAll();
    User findByLogin(String login);
    void create(User user);
    void update(User user);
    void delete(String login);
    User login(String login, String password);
//    boolean changePassword(String login, String oldPassword, String newPassword);
}
