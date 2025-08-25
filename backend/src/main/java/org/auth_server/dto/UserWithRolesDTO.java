package org.auth_server.dto;

import org.auth_server.entity.User;

import java.util.List;

public class UserWithRolesDTO {
    private User user;
    private List<String> roles;

    public UserWithRolesDTO(User user, List<String> roles) {
        this.user = user;
        this.roles = roles;
    }

    public UserWithRolesDTO() {}

    public User getUser() {
        return user;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
