package org.auth_server.dao;

import org.auth_server.entity.Role;

import java.util.List;

public interface RoleDao {
    List<Role> findAllRoles();
    Role findRoleById(int id);
    Role findRoleByName(String roleName);
}
