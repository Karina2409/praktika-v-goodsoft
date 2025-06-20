package org.auth_server.web.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.auth_server.entity.User;
import org.auth_server.services.UserRoleService;
import org.auth_server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Objects;

@WebServlet (name = "LoginServlet", urlPatterns = {"/login.jhtml"})
public class LoginServlet extends HttpServlet {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        req.removeAttribute("error");

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        User newUser = userService.login(login, password);

        if (newUser != null) {
            req.getSession().setAttribute("user", newUser);

            var roles = userRoleService.findRolesByUserId(newUser.getUserId());
            req.getSession().setAttribute("roles", roles);
            for (var role : roles) {
                if (Objects.equals(role.getName(), "Администратор")) {
                    req.getSession().setAttribute("isAdmin", true);
                }
            }

            resp.sendRedirect(req.getContextPath() + "/WEB-INF.jhtml");
        } else {
            req.setAttribute("error", "Неверные логин или пароль");
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
        }
    }
}
