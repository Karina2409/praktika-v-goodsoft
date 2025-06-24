package org.auth_server.dao;

import org.auth_server.dao.impl.RoleDaoImpl;
import org.auth_server.dao.impl.UserDaoImpl;
import org.auth_server.dao.impl.UserRoleDaoImpl;

public class DaoFactory {

    private final static DaoFactory instance = new DaoFactory();

    public static DaoFactory getInstance() {
        return instance;
    }

    public UserDao getUserDao() {
        return UserDaoImpl.getInstance();
    }

    public RoleDao getRoleDao() {
        return RoleDaoImpl.getInstance();
    }

    public UserRoleDao getUserRoleDao() {
        return UserRoleDaoImpl.getInstance();
    }
}
