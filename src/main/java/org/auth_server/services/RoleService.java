package org.auth_server.services;

import org.auth_server.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAllRoles();
    Role findRoleById(int id);
    Role findRoleByName(String roleName);
}
