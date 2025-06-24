package org.auth_server.dao;

import org.auth_server.entity.Role;

import java.util.List;

public interface UserRoleDao {
    List<Role> findRolesByUserId(int userId);
    void addRolesToUser(int userId, int[] roleIds);
    void removeAllRolesFromUser(int userId);
}
