package org.auth_server.web.filters;

import org.auth_server.entity.User;
import org.auth_server.entity.enums.Role;
import org.auth_server.services.UserService;
import org.auth_server.services.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter (filterName = "AuthFilter", urlPatterns = {"*.jhtml"})
public class AuthFilter implements Filter {

    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getServletPath();

        boolean isLoginRequest = path.equals("/login.jhtml") || path.equals("/doLogin.jhtml") || path.equals("/index.jsp");
        HttpSession session = req.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute("user") != null);

        boolean isNormalUserPage = path.equals("/welcome.jhtml")
                || path.equals("/do-password-edit.jhtml")
                || path.equals("/password-edit.jhtml")
                || path.equals("/logout.jhtml");

        boolean isNormalAdminPage = path.equals("/users.jhtml")
                || path.equals("/edit-user.jhtml")
                || path.equals("/delete-user.jhtml")
                || path.equals("/add-user.jhtml")
                || path.equals("/doEdit-user.jhtml")
                || path.equals("/doAdd-user.jhtml");

        if(loggedIn) {
            User user = (User) session.getAttribute("user");
            Role role = null;
            if (user != null) {
                for (User user1 : userService.getAllUsers()) {
                    if (user1.getLogin().equals(user.getLogin())) {
                        role = user1.getRole();
                    }
                }
            }
            if (role == Role.USER) {

                if (isNormalUserPage || isLoginRequest) {
                    chain.doFilter(request, response);
                } else {
                    resp.sendRedirect(req.getContextPath() + "/welcome.jhtml");
                }
            } else {
                if (isNormalUserPage || isLoginRequest || isNormalAdminPage) {
                    chain.doFilter(request, response);
                } else {
                    resp.sendRedirect(req.getContextPath() + "/welcome.jhtml");
                }
            }
        } else if (isLoginRequest) {
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect(req.getContextPath() + "/login.jhtml");
        }
    }
}
