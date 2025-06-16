package org.auth_server.dao;

import org.auth_server.dao.impl.InMemoryStorage;

public class DaoFactory {

    private final static DaoFactory instance = new DaoFactory();

    public static DaoFactory getInstance() {
        return instance;
    }

    public UserDao getUserDao() {
        return InMemoryStorage.getInstance();
    }
}
