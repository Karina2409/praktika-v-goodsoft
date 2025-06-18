package org.auth_server.dao.impl;

import org.auth_server.dao.UserDao;
import org.auth_server.entity.User;
import org.auth_server.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final UserDaoImpl instance = new UserDaoImpl();

    private UserDaoImpl() {}

    public static UserDaoImpl getInstance() {
        return instance;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<User>();

        String sql = "SELECT * FROM users";

        try (
                Connection connection = DBUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                users.add(rowToUser(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public User findByLogin(String login) {
        String sql = "SELECT * FROM users WHERE login = ?";

        try (
                Connection cn = DBUtil.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql);
        ) {
            ps.setString(1, login);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rowToUser(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(User user) {
        String sql = "INSERT INTO users (login, password, name, birthday, age, salary) VALUES (?, ?, ?, ?, ?, ?)";
        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setDate(4, user.getBirthday() != null ? Date.valueOf(user.getBirthday()) : null);
            ps.setInt(5, user.getAge());
            ps.setDouble(6, user.getSalary());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE users SET name = ?, birthday = ?, age = ?, salary = ? WHERE login = ?";
        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, user.getName());
            ps.setDate(2, user.getBirthday() != null ? Date.valueOf(user.getBirthday()) : null);
            ps.setInt(3, user.getAge());
            ps.setDouble(4, user.getSalary());
            ps.setString(5, user.getLogin());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String login) {
        String sql = "DELETE FROM users WHERE login = ?";
        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, login);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User login(String login, String password) {
        String sql = "SELECT * FROM users WHERE login = ? AND password = ?";
        try (
                Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rowToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//    @Override
//    public boolean changePassword(String login, String oldPassword, String newPassword) {
//        for (User user : users) {
//            if (user.getLogin().equals(login) && user.getPassword().equals(oldPassword)) {
//                if (!oldPassword.equals(newPassword)) {
//                    user.setPassword(newPassword);
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

    private User rowToUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("login"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getDate("birthday") != null ? rs.getDate("birthday").toLocalDate() : null,
                rs.getInt("age"),
                rs.getDouble("salary")
        );
    }
}
