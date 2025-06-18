package org.auth_server.web.servlets;

import org.auth_server.entity.User;
import org.auth_server.services.ServiceFactory;
import org.auth_server.services.UserRoleService;
import org.auth_server.services.UserService;

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

    private final UserService userService = ServiceFactory.getInstance().getUserService();
    private final UserRoleService userRoleService = ServiceFactory.getInstance().getUserRoleService();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            String login = req.getParameter("login");
            String password = req.getParameter("password");
            String name = req.getParameter("name");
            String birthdayStr = req.getParameter("birthday");
            int age = Integer.parseInt(req.getParameter("age"));
            double salary = Double.parseDouble(req.getParameter("salary"));

            if (birthdayStr == null || birthdayStr.isEmpty()) {
                throw new IllegalArgumentException("Дата рождения обязательна");
            }

            LocalDate birthday = LocalDate.parse(birthdayStr);

            String[] roleIdStrings = req.getParameterValues("roles");
            int[] roleIds = new int[0];
            if (roleIdStrings != null) {
                roleIds = new int[roleIdStrings.length];
                for (int i = 0; i < roleIdStrings.length; i++) {
                    roleIds[i] = Integer.parseInt(roleIdStrings[i]);
                }
            }

            User user = new User(login, password, name, birthday, age, salary);
            User newUser = userService.addUser(user);

            if (newUser != null) {
                userRoleService.addRolesToUser(newUser.getUserId(), roleIds);
                resp.sendRedirect("users.jhtml");
            } else {
                req.setAttribute("error", "Пользователь с таким логином уже существует");
                req.setAttribute("add", true);
                req.getRequestDispatcher(JSP_PATH.getPath() + "/edit-user.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            req.setAttribute("error", "Неверные данные: " + e.getMessage());
            req.setAttribute("add", true);
            req.getRequestDispatcher(JSP_PATH.getPath() + "/edit-user.jsp").forward(req, resp);
        }
    }
}
