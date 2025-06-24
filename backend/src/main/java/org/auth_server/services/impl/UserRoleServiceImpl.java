package org.auth_server.services.impl;

import org.auth_server.dao.UserRoleMapper;
import org.auth_server.entity.Role;
import org.auth_server.services.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    public UserRoleMapper userRoleDao;

    @Override
    public List<String> findRolesByUserId(int userId) {
        return userRoleDao.findRolesByUserId(userId).stream()
                .map(Role::getName)
                .toList();
    }

    @Override
    public List<Role> findRolesByUser(int userId) {
        return userRoleDao.findRolesByUserId(userId);
    }

    @Override
    public void addRolesToUser(int userId, int[] roleIds) {
        userRoleDao.addRolesToUser(userId, roleIds);
    }

    @Override
    public void removeAllRolesFromUser(int userId) {
        userRoleDao.removeAllRolesFromUser(userId);
    }
}
