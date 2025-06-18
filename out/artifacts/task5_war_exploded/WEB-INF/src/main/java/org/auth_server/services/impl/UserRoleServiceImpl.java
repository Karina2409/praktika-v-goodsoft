package org.auth_server.services.impl;

import org.auth_server.dao.DaoFactory;
import org.auth_server.dao.UserRoleDao;
import org.auth_server.entity.Role;
import org.auth_server.services.UserRoleService;

import java.util.List;

public class UserRoleServiceImpl implements UserRoleService {
    private final static UserRoleServiceImpl instance = new UserRoleServiceImpl();
    public final UserRoleDao userRoleDao = DaoFactory.getInstance().getUserRoleDao();

    private UserRoleServiceImpl() {}

    public static UserRoleServiceImpl getInstance() {
        return instance;
    }

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
