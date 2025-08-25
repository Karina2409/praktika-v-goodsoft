package org.auth_server.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.auth_server.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> findAll();
    User findByLogin(String login);
    User findById(int id);
    void create(User user);
    void update(User user);
    boolean delete(String login);
    User login(@Param("login") String login, @Param("password") String password);
}
