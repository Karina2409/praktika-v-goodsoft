package org.auth_server.services;

import org.auth_server.dao.InMemoryStorage;
import org.auth_server.models.User;

import javax.servlet.http.HttpSession;

public class SecurityService {

    public User login (String login, String password, HttpSession session) {
        User sessionUser = (User) session.getAttribute("user");

        if (sessionUser != null && sessionUser.getLogin().equals(login)) {
            return sessionUser.getPassword().equals(password) ? sessionUser : null;
        }

        for (User user : InMemoryStorage.users) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public boolean changePassword (String login, String oldPassword, String newPassword) {
        for (User user : InMemoryStorage.users) {
            if (user.getLogin().equals(login) && user.getPassword().equals(oldPassword)) {
                if (!oldPassword.equals(newPassword)) {
                    user.setPassword(newPassword);
                    return true;
                }
            }
        }
        return false;
    }
}
