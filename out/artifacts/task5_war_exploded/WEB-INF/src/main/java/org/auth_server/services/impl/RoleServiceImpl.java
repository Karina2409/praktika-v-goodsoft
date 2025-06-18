package org.auth_server.services.impl;

import org.auth_server.dao.DaoFactory;
import org.auth_server.dao.RoleDao;
import org.auth_server.entity.Role;
import org.auth_server.services.RoleService;

import java.util.List;

public class RoleServiceImpl implements RoleService {
    private final static RoleServiceImpl instance = new RoleServiceImpl();
    private final RoleDao roleDao = DaoFactory.getInstance().getRoleDao();

    private RoleServiceImpl() {}

    public static RoleServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<Role> findAllRoles() {
        return roleDao.findAllRoles();
    }

    @Override
    public Role findRoleById(int id) {
        return roleDao.findRoleById(id);
    }

    @Override
    public Role findRoleByName(String roleName) {
        return roleDao.findRoleByName(roleName);
    }
}
