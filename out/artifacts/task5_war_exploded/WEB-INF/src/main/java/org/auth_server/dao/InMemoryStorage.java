package org.auth_server.dao;

import org.auth_server.models.User;
import org.auth_server.models.enums.Role;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InMemoryStorage {
    public final static List<User> users = new ArrayList<>(List.of(
            new User("karina", "1234", "kserduk@gmail.com", "Куприянова", "Карина", "Владимировна", LocalDate.of(2004, 9, 24), Role.ADMIN),
            new User("ivan", "123", "ivan@mail.ru", "Иванов", "Иван", "Иванович", LocalDate.of(2000, 3, 23), Role.USER),
            new User("kate", "1212", "kate@mail.ru", "Смирнова", "Екатерина", "Евгеньевна", LocalDate.of(2001, 5, 2), Role.USER),
            new User("max", "1122", "max@mail.ru", "Петров", "Максим", "Дмитриевич", LocalDate.of(2003, 6, 5), Role.USER),
            new User("petr", "2222", "petr@mail.ru", "Семенов", "Петр", "Егорович", LocalDate.of(2001, 1, 8), Role.USER)
    ));
}
