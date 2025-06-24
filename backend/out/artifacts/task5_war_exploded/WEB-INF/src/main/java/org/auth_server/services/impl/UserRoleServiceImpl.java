package org.auth_server.services.impl;

import org.auth_server.dao.UserRoleDao;
import org.auth_server.entity.Role;
import org.auth_server.services.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    public UserRoleDao userRoleDao;

    @Override
    public List<Role> findRolesByUserId(int userId) {
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
