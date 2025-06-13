package org.auth_server.entity.enums;

public enum Path {
    WELCOME_PAGE("/welcome"),
    JSP_PATH("WEB-INF/jsp"),
    LOGIN_PAGE("/login"),
    PASSWORDEDIT_PAGE("/password-edit"),
    LOGOUT_PAGE("/logout"),
    USERS_PAGE("/users"),;

    private final String path;

    Path(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
