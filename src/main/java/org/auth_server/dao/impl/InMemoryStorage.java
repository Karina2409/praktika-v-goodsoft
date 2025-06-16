package org.auth_server.dao.impl;

import org.auth_server.dao.UserDao;
import org.auth_server.entity.User;
import org.auth_server.entity.enums.Role;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InMemoryStorage implements UserDao {
    private static InMemoryStorage instance = new InMemoryStorage();
    public final static List<User> users = new ArrayList<>(List.of(
            new User("karina", "1234", "kserduk@gmail.com", "Куприянова", "Карина", "Владимировна", LocalDate.of(2004, 9, 24), Role.ADMIN),
            new User("ivan", "123", "ivan@mail.ru", "Иванов", "Иван", "Иванович", LocalDate.of(2000, 3, 23), Role.USER),
            new User("kate", "1212", "kate@mail.ru", "Смирнова", "Екатерина", "Евгеньевна", LocalDate.of(2001, 5, 2), Role.USER),
            new User("max", "1122", "max@mail.ru", "Петров", "Максим", "Дмитриевич", LocalDate.of(2003, 6, 5), Role.USER),
            new User("petr", "2222", "petr@mail.ru", "Семенов", "Петр", "Егорович", LocalDate.of(2001, 1, 8), Role.USER)
    ));

    private InMemoryStorage() {}

    public static InMemoryStorage getInstance() {
         return instance;
    }

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public User getByLogin(String login) {
        return users.stream().filter(user -> user.getLogin().equals(login)).findFirst().orElse(null);
    }

    @Override
    public void create(User user) {
        users.add(user);
    }

    @Override
    public void update(User user) {
        for (User u : users) {
            if (u.getLogin().equals(user.getLogin())) {
                u.setEmail(user.getEmail());
                u.setName(user.getName());
                u.setSurname(user.getSurname());
                u.setPatronymic(user.getPatronymic());
                u.setBirthday(user.getBirthday());
                u.setRole(user.getRole());
            }
        }
    }

    @Override
    public void delete(String login) {
        users.removeIf(user -> user.getLogin().equals(login));
    }

    @Override
    public User login(String login, String password) {
        for (User user : users) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                return user;
            }
        }

        return null;
    }

    @Override
    public boolean changePassword(String login, String oldPassword, String newPassword) {
        for (User user : users) {
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
