package org.auth_server.entity;

import jakarta.validation.constraints.*;
import org.auth_server.validation.Adult;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class User {

    private int userId;

    private String login;

    private String password;

    @Size(min = 3, max = 20, message = "Имя не может быть короче 3 символов")
    @NotNull(message = "Имя не может быть пустым")
    private String name;

    @NotNull(message = "Дата рождения не может быть пустой")
    @Past(message = "Дата должна быть в прошлом")
    @Adult
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private int age;

    @NotNull(message = "Зарплата не может быть пустой")
    @Min(value = 0, message = "Зарплата не может быть меньше 0")
    private double salary;

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

    public User() {
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "user id: " + userId
        + "\nlogin: " + login
        + "\npassword: " + password
        + "\nname: " + name
        + "\nbirthday: " + birthday
        + "\nage: " + age
        + "\nsalary: " + salary;
    }
}
