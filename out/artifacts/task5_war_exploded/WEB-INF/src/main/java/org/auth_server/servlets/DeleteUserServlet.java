package org.auth_server.servlets;

import org.auth_server.dao.InMemoryStorage;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteUserServlet", urlPatterns = {"/delete-user.jhtml"})
public class DeleteUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        if (login != null) {
            InMemoryStorage.users.removeIf(user -> user.getLogin().equals(login));
        }
        resp.sendRedirect("/users.jhtml");
    }
}
