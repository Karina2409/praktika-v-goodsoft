package org.auth_server.web.servlets;

import org.auth_server.entity.User;
import org.auth_server.services.UserService;
import org.auth_server.services.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.auth_server.entity.enums.Path.*;

@WebServlet (name = "LoginServlet", urlPatterns = {"/doLogin.jhtml"})
public class LoginServlet extends HttpServlet {

    UserService userService;

    @Override
    public void init() {
        this.userService = new UserServiceImpl();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.removeAttribute("error");
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        User newUser = userService.login(login, password);

        if (newUser != null) {
            req.getSession().setAttribute("user", newUser);
            req.getSession().setAttribute("role", String.valueOf(newUser.getRole()));
            resp.sendRedirect(req.getContextPath() + WELCOME_PAGE.getPath() + ".jhtml");
        } else {
            req.setAttribute("error", "Invalid username or password");
            req.getRequestDispatcher(JSP_PATH.getPath() + LOGIN_PAGE.getPath() + ".jsp").forward(req, resp);
        }
    }
}
