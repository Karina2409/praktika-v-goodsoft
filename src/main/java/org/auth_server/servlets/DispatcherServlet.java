package org.auth_server.servlets;

import org.auth_server.dao.InMemoryStorage;
import org.auth_server.models.enums.Role;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.auth_server.models.enums.Path.JSP_PATH;
import static org.auth_server.models.enums.Path.USERS_PAGE;

@WebServlet(name = "DispatcherServlet", urlPatterns = {"*.jhtml"})
public class DispatcherServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        String jspPage = JSP_PATH.getPath() + path.replace(".jhtml", ".jsp");

        if (path.equals(USERS_PAGE.getPath() + ".jhtml")) {
            req.setAttribute("users", InMemoryStorage.users);
        }

        if (path.equals("/edit-user.jhtml")) {
            String login = req.getParameter("login");
            var user = InMemoryStorage.users.stream()
                    .filter(u -> u.getLogin().equals(login))
                    .findFirst()
                    .orElse(null);
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
