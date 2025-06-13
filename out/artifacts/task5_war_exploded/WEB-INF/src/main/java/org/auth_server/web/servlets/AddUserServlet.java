package org.auth_server.web.servlets;

import org.auth_server.entity.User;
import org.auth_server.entity.enums.Role;
import org.auth_server.services.UserService;
import org.auth_server.services.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

import static org.auth_server.entity.enums.Path.JSP_PATH;

@WebServlet(name = "AddUserServlet", urlPatterns = {"/doAdd-user.jhtml"})
public class AddUserServlet extends HttpServlet {

    private final UserService userService = UserServiceImpl.getInstance();

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

        User user = new User(
            req.getParameter("login"),
            req.getParameter("password"),
            req.getParameter("email"),
            req.getParameter("surname"),
            req.getParameter("name"),
            req.getParameter("patronymic"),
            birthday,
            role
        );

        User newUser = userService.addUser(user);
        if (newUser != null) {
            resp.sendRedirect("users.jhtml");
        } else {
            req.setAttribute("error", "User with this login already exists");
            req.setAttribute("add", true);
            req.getRequestDispatcher(JSP_PATH.getPath() + "/edit-user" + ".jsp").forward(req, resp);
        }


    }
}
