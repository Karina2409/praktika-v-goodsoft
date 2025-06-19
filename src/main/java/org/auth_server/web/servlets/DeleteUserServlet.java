package org.auth_server.web.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.auth_server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@WebServlet(name = "DeleteUserServlet", urlPatterns = {"/delete-user.jhtml"})
public class DeleteUserServlet extends HttpServlet {
    @Autowired
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");

        userService.deleteUser(login);

        resp.sendRedirect("/users.jhtml");
    }
}
