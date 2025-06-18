package org.auth_server.services;

import org.auth_server.entity.Role;

import java.util.List;

public interface UserRoleService {
    List<Role> findRolesByUserId(int userId);
    void addRolesToUser(int userId, int[] roleIds);
    void removeAllRolesFromUser(int userId);
}
