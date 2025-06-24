package org.auth_server.dto;

import org.auth_server.entity.Role;
import org.auth_server.entity.User;

import java.util.List;

public class UserWithRolesDTO {
    private User user;
    private List<Role> roles;

    public UserWithRolesDTO(User user, List<Role> roles) {
        this.user = user;
        this.roles = roles;
    }

    public User getUser() {
        return user;
    }

    public List<Role> getRoles() {
        return roles;
    }
}
