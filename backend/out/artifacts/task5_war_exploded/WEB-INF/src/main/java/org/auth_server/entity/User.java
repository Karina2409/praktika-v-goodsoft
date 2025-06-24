package org.auth_server.entity;

import java.time.LocalDate;

public class User {
    private int userId;
    private final String login;
    private String password;
//    private String email;
//    private String surname;
    private String name;
//    private String patronymic;
    private LocalDate birthday;
    private int age;
    private double salary;
//    private Role role;

    public User(int userId, String login, String password, String name, LocalDate birthday, int age, double salary) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.name = name;
        this.birthday = birthday;
        this.age = age;
        this.salary = salary;
    }

    public User(String login, String password, String name, LocalDate birthday, int age, double salary) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.birthday = birthday;
        this.age = age;
        this.salary = salary;
    }

    public User(String login, String name, LocalDate birthday, int age, double salary) {
        this.name = name;
        this.birthday = birthday;
        this.age = age;
        this.salary = salary;
        this.login = login;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    //    public User(String login,
//                String password,
//                String email,
//                String surname,
//                String name,
//                String patronymic,
//                LocalDate birthday,
//                Role role) {
//        this.login = login;
//        this.password = password;
//        this.email = email;
//        this.surname = surname;
//        this.name = name;
//        this.patronymic = patronymic;
//        this.birthday = birthday;
//        this.role = role;
//    }
//
//    public User(String login,
//                String email,
//                String surname,
//                String name,
//                String patronymic,
//                LocalDate birthday,
//                Role role) {
//        this.login = login;
//        this.email = email;
//        this.surname = surname;
//        this.name = name;
//        this.patronymic = patronymic;
//        this.birthday = birthday;
//        this.role = role;
//    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getSurname() {
//        return surname;
//    }
//
//    public void setSurname(String surname) {
//        this.surname = surname;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getPatronymic() {
//        return patronymic;
//    }
//
//    public void setPatronymic(String patronymic) {
//        this.patronymic = patronymic;
//    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

//    public Role getRole() {
//        return role;
//    }
//
//    public void setRole(Role role) {
//        this.role = role;
//    }
}
