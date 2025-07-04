package org.auth_server.services;

import org.auth_server.services.impl.RoleServiceImpl;
import org.auth_server.services.impl.UserRoleServiceImpl;
import org.auth_server.services.impl.UserServiceImpl;

public class ServiceFactory {
    private final static ServiceFactory instance = new ServiceFactory();

    public static ServiceFactory getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return UserServiceImpl.getInstance();
    }

    public RoleService getRoleService() {
        return RoleServiceImpl.getInstance();
    }

    public UserRoleService getUserRoleService() {
        return UserRoleServiceImpl.getInstance();
    }
}
