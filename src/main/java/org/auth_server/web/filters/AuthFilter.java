package org.auth_server.web.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.auth_server.entity.Role;
import org.auth_server.entity.User;
import org.auth_server.services.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.List;

@Component
public class AuthFilter implements Filter {

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        String path = req.getServletPath();

        if (path.matches(".*\\.(css|ico|png|jpe?g|svg|gif|webp|bmp|avif|tiff|jfif)$")) {
            chain.doFilter(request, response);
            return;
        }

        boolean isLoginRequest = path.equals("/login") || path.equals("/doLogin");
        boolean loggedIn = (session != null && session.getAttribute("user") != null);

        if(loggedIn) {
            User user = (User) session.getAttribute("user");
            List<Role> roles = userRoleService.findRolesByUserId(user.getUserId());

            boolean isUserPage = path.matches("^/(welcome|logout)(/)?$");
            boolean isAdminPage = path.matches("^/users(/.*)?$");

            boolean isAdmin = roles.stream().anyMatch(role -> role.getName().equalsIgnoreCase("Администратор"));
            boolean isUser = !roles.isEmpty();

            if (isAdmin) {
                if (isAdminPage || isUserPage) {
                    chain.doFilter(request, response);
                } else {
                    resp.sendRedirect(req.getContextPath() + "/welcome");
                }
            } else if (isUser) {
                if (isUserPage) {
                    chain.doFilter(request, response);
                } else {
                    resp.sendRedirect(req.getContextPath() + "/welcome");
                }
            } else {
                resp.sendRedirect(req.getContextPath() + "/login");
            }
        } else if (isLoginRequest) {
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}
