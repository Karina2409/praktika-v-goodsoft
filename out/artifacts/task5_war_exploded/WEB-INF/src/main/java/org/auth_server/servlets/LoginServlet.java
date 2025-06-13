package org.auth_server.servlets;

import org.auth_server.models.User;
import org.auth_server.services.SecurityService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.auth_server.models.enums.Path.*;

@WebServlet (name = "LoginServlet", urlPatterns = {"/doLogin.jhtml"})
public class LoginServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.removeAttribute("error");
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        HttpSession session = req.getSession();
        SecurityService securityService = new SecurityService();
        User newUser = securityService.login(login, password, session);

        if (newUser != null) {
            req.getSession().setAttribute("user", newUser);
            resp.sendRedirect(req.getContextPath() + WELCOME_PAGE.getPath() + ".jhtml");
        } else {
            req.setAttribute("error", "Invalid username or password");
            req.getRequestDispatcher(JSP_PATH.getPath() + LOGIN_PAGE.getPath() + ".jsp").forward(req, resp);
        }
    }
}
