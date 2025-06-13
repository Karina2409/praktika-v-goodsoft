package org.auth_server.servlets;

import org.auth_server.dao.InMemoryStorage;
import org.auth_server.models.User;
import org.auth_server.models.enums.Role;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name = "AddUserServlet", urlPatterns = {"/doAdd-user.jhtml"})
public class AddUserServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String birthdayStr = req.getParameter("birthday");
        LocalDate birthday;
        if (birthdayStr != null && !birthdayStr.isEmpty()) {
            birthday = LocalDate.parse(birthdayStr);
        } else {
            return;
        }

        String roleParam = req.getParameter("role");
        Role role;
        if (roleParam != null && !roleParam.isEmpty()) {
            role = Role.valueOf(roleParam);
        } else {
            return;
        }

        InMemoryStorage.users.add(new User(
                req.getParameter("login"),
                req.getParameter("password"),
                req.getParameter("email"),
                req.getParameter("surname"),
                req.getParameter("name"),
                req.getParameter("patronymic"),
                birthday,
                role
        ));

        resp.sendRedirect("users.jhtml");
    }
}
