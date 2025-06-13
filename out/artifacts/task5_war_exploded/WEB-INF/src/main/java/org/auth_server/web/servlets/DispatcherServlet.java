package org.auth_server.web.servlets;

import org.auth_server.entity.enums.Role;
import org.auth_server.services.UserService;
import org.auth_server.services.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.auth_server.entity.enums.Path.JSP_PATH;
import static org.auth_server.entity.enums.Path.USERS_PAGE;

@WebServlet(name = "DispatcherServlet", urlPatterns = {"*.jhtml"})
public class DispatcherServlet extends HttpServlet {

    UserService userService;

    @Override
    public void init() {
        this.userService = new UserServiceImpl();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        String jspPage = JSP_PATH.getPath() + path.replace(".jhtml", ".jsp");

        if (path.equals(USERS_PAGE.getPath() + ".jhtml")) {
            req.setAttribute("users", userService.getAllUsers());
        }

        if (path.equals("/edit-user.jhtml")) {
            String login = req.getParameter("login");
            var user = userService.getUserByLogin(login);
            req.setAttribute("user", user);
            req.setAttribute("roles", Role.values());
        }

        if (path.equals("/add-user.jhtml")) {
            req.setAttribute("roles", Role.values());
            req.setAttribute("add", true);
            req.getRequestDispatcher(JSP_PATH.getPath() + "/edit-user.jsp").forward(req, resp);
            return;
        }

        req.getRequestDispatcher(jspPage).forward(req, resp);
    }
}
