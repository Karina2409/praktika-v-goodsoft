package org.auth_server.services.impl;

import org.auth_server.dao.RoleMapper;
import org.auth_server.entity.Role;
import org.auth_server.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleDao;

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
