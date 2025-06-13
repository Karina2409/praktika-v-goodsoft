package org.auth_server.servlets;

import org.auth_server.dao.InMemoryStorage;
import org.auth_server.models.User;
import org.auth_server.models.enums.Role;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet (name = "EditUserServlet", urlPatterns = {"/doEdit-user.jhtml"})
public class EditUserServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String login = req.getParameter("login");

        for (User user : InMemoryStorage.users) {
            if (user.getLogin().equals(login)) {
                user.setEmail(req.getParameter("email"));
                user.setSurname(req.getParameter("surname"));
                user.setName(req.getParameter("name"));
                user.setPatronymic(req.getParameter("patronymic"));

                String birthdayStr = req.getParameter("birthday");
                if (birthdayStr != null && !birthdayStr.isEmpty()) {
                    user.setBirthday(LocalDate.parse(birthdayStr));
                }

                String roleParam = req.getParameter("role");
                if (roleParam != null && !roleParam.isEmpty()) {
                    user.setRole(Role.valueOf(roleParam));
                }
                break;
            }
        }
        resp.sendRedirect("users.jhtml");
    }
}
