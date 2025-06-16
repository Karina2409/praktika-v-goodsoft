package org.auth_server.web.servlets;

import org.auth_server.services.ServiceFactory;
import org.auth_server.services.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteUserServlet", urlPatterns = {"/delete-user.jhtml"})
public class DeleteUserServlet extends HttpServlet {
    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");

        userService.deleteUser(login);

        resp.sendRedirect("/users.jhtml");
    }
}
