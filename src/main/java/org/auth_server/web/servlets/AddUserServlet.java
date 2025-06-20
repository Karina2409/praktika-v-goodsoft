package org.auth_server.web.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.auth_server.entity.User;
import org.auth_server.services.RoleService;
import org.auth_server.services.UserRoleService;
import org.auth_server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

@WebServlet(name = "AddUserServlet", urlPatterns = {"/add-user.jhtml"})
public class AddUserServlet extends HttpServlet {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roles", roleService.findAllRoles());
        req.setAttribute("add", true);
        req.getRequestDispatcher("/WEB-INF/jsp/edit-user.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            req.setCharacterEncoding("UTF-8");

            String login = req.getParameter("login");
            String password = req.getParameter("password");
            String name = req.getParameter("name");
            String birthdayStr = req.getParameter("birthday");
            double salary = Double.parseDouble(req.getParameter("salary"));

            if (birthdayStr == null || birthdayStr.isEmpty()) {
                throw new IllegalArgumentException("Дата рождения обязательна");
            }

            LocalDate birthday = LocalDate.parse(birthdayStr);

            int age = Period.between(birthday, LocalDate.now()).getYears();

            if (age < 18) {
                throw new IllegalArgumentException("Пользователь должен быть старше 18 лет");
            }

            String[] roleIdParams = req.getParameterValues("roleIds");
            int[] roleIds = new int[0];
            if (roleIdParams != null) {
                roleIds = new int[roleIdParams.length];
                for (int i = 0; i < roleIdParams.length; i++) {
                    roleIds[i] = Integer.parseInt(roleIdParams[i]);
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
                req.setAttribute("roles", roleService.findAllRoles());
                req.getRequestDispatcher("/WEB-INF/jsp/edit-user.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            req.setAttribute("error", "Неверные данные: " + e.getMessage());
            req.setAttribute("add", true);
            req.getRequestDispatcher("/WEB-INF/jsp/edit-user.jsp").forward(req, resp);
        }
    }
}
