package org.auth_server.web.servlets;

import org.auth_server.entity.User;
import org.auth_server.services.UserRoleService;
import org.auth_server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static org.auth_server.entity.enums.Path.*;

@WebServlet (name = "LoginServlet", urlPatterns = {"/doLogin.jhtml"})
public class LoginServlet extends HttpServlet {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public void init() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
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

            resp.sendRedirect(req.getContextPath() + WELCOME_PAGE.getPath() + ".jhtml");
        } else {
            req.setAttribute("error", "Неверные логин или пароль");
            req.getRequestDispatcher(JSP_PATH.getPath() + LOGIN_PAGE.getPath() + ".jsp").forward(req, resp);
        }
    }
}
