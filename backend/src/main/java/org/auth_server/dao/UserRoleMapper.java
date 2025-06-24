package org.auth_server.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.auth_server.entity.Role;

import java.util.List;

@Mapper
public interface UserRoleMapper {
    List<Role> findRolesByUserId(int userId);
    void addRolesToUser(@Param("userId") int userId, @Param("roleIds") int[] roleIds);
    void removeAllRolesFromUser(int userId);
}
