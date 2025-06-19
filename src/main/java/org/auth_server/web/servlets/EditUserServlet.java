package org.auth_server.web.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.auth_server.entity.Role;
import org.auth_server.entity.User;
import org.auth_server.services.RoleService;
import org.auth_server.services.UserRoleService;
import org.auth_server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet (name = "EditUserServlet", urlPatterns = {"/edit-user.jhtml"})
public class EditUserServlet extends HttpServlet {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        var user = userService.getUserByLogin(login);
        req.setAttribute("user", user);
        req.setAttribute("roles", roleService.findAllRoles());

        var userRoles = userRoleService.findRolesByUserId(user.getUserId())
                .stream()
                .map(Role::getName)
                .toList();

        req.setAttribute("userRoles", userRoles);
        req.getRequestDispatcher("/WEB-INF/jsp/edit-user.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        String login = req.getParameter("login");
        String name = req.getParameter("name");
        LocalDate birthday = LocalDate.parse(req.getParameter("birthday"));
        int age = Integer.parseInt(req.getParameter("age"));
        double salary = Double.parseDouble(req.getParameter("salary"));

        User user = userService.getUserByLogin(login);

        if (user != null) {
            user.setName(name);
            user.setBirthday(birthday);
            user.setAge(age);
            user.setSalary(salary);

            userService.updateUser(user);

            String[] roleIdParams = req.getParameterValues("roleIds");
            if (roleIdParams != null) {
                int[] newRoleIds = new int[roleIdParams.length];
                for (int i = 0; i < roleIdParams.length; i++) {
                    newRoleIds[i] = Integer.parseInt(roleIdParams[i]);
                }

                userRoleService.removeAllRolesFromUser(user.getUserId());

                userRoleService.addRolesToUser(user.getUserId(), newRoleIds);
            }
        }

        resp.sendRedirect("users.jhtml");
    }
}
