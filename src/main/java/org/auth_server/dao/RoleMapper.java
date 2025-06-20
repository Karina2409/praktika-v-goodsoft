package org.auth_server.dao;

import org.apache.ibatis.annotations.Mapper;
import org.auth_server.entity.Role;

import java.util.List;

@Mapper
public interface RoleMapper {
    List<Role> findAllRoles();
    Role findRoleById(int id);
    Role findRoleByName(String roleName);
}
