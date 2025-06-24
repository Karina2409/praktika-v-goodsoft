package org.auth_server.dao.impl;

import org.auth_server.dao.RoleDao;
import org.auth_server.entity.Role;
import org.auth_server.utils.DBUtil;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {
    @Override
    public List<Role> findAllRoles() {
        List<Role> roles = new ArrayList<>();
        String sql = "SELECT id, name FROM roles";

        try (
                Connection cn = DBUtil.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Role role = new Role(
                        rs.getInt("id"),
                        rs.getString("name")
                );
                roles.add(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roles;
    }

    @Override
    public Role findRoleById(int id) {
        String sql = "select * from roles where id = ?";

        try (
                Connection cn = DBUtil.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql);
        ) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Role(rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Role findRoleByName(String name) {
        String sql = "select * from role where name = ?";

        try (
                Connection cn = DBUtil.getConnection();
                PreparedStatement ps = cn.prepareStatement(sql);
        ) {
            ps.setString(1, name);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Role(rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
