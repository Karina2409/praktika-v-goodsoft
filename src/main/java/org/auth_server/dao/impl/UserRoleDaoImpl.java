package org.auth_server.dao.impl;

import org.auth_server.dao.UserRoleDao;
import org.auth_server.entity.Role;
import org.auth_server.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRoleDaoImpl implements UserRoleDao {
    private static final UserRoleDaoImpl instance = new UserRoleDaoImpl();

    private UserRoleDaoImpl() {}

    public static UserRoleDaoImpl getInstance() {
        return instance;
    }

    @Override
    public List<Role> findRolesByUserId(int userId) {
        List<Role> roles = new ArrayList<Role>();
        String sql = """
                    select r.name 
                    from users_roles ur
                    join roles r on r.id = ur.role_id
                    where ur.user_id=?
                """;

        try (
                Connection cn = DBUtil.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql);
        ) {
            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    roles.add(new Role(rs.getString("name")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roles;
    }

    @Override
    public void addRolesToUser(int userId, int[] roleIds) {
        String sql = "insert into users_roles(user_id, role_id) values(?, ?)";

        try (
                Connection cn = DBUtil.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql);
        ) {
            for (long roleId : roleIds) {
                ps.setLong(1, userId);
                ps.setLong(2, roleId);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeAllRolesFromUser(int userId) {
        String sql = "DELETE FROM users_roles WHERE user_id = ?";
        try (Connection cn = DBUtil.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
