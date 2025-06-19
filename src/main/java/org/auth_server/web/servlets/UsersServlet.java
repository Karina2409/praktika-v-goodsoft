package org.auth_server.web.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.auth_server.dto.UserWithRolesDTO;
import org.auth_server.services.UserRoleService;
import org.auth_server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@WebServlet(name = "UsersServlet", urlPatterns = {"/users.jhtml"})
public class UsersServlet extends HttpServlet {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var users = userService.getAllUsers();

        var usersWithRoles = users.stream()
                .map(user -> new UserWithRolesDTO(
                        user,
                        userRoleService.findRolesByUserId(user.getUserId())
                ))
                .toList();
        req.setAttribute("users", usersWithRoles);

        req.getRequestDispatcher("/WEB-INF/jsp/users.jsp").forward(req, resp);
    }
}
