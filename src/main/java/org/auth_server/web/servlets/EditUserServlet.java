package org.auth_server.web.servlets;

import org.auth_server.entity.User;
import org.auth_server.entity.enums.Role;
import org.auth_server.services.UserService;
import org.auth_server.services.impl.UserServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet (name = "EditUserServlet", urlPatterns = {"/doEdit-user.jhtml"})
public class EditUserServlet extends HttpServlet {

    UserService userService;

    @Override
    public void init() {
        this.userService = new UserServiceImpl();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        User user = new User(
                req.getParameter("login"),
                req.getParameter("email"),
                req.getParameter("surname"),
                req.getParameter("name"),
                req.getParameter("patronymic"),
                LocalDate.parse(req.getParameter("birthday")),
                Role.valueOf(req.getParameter("role"))
        );

        userService.updateUser(user);

        resp.sendRedirect("users.jhtml");
    }
}
