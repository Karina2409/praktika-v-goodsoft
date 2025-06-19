package org.auth_server.entity.enums;

public enum Path {
    WELCOME_PAGE("/welcome"),
    JSP_PATH("src/main/webapp/WEB-INF/jsp"),
    LOGIN_PAGE("/login"),
    PASSWORDEDIT_PAGE("/password-edit"),
    LOGOUT_PAGE("/logout");

    private final String path;

    Path(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
