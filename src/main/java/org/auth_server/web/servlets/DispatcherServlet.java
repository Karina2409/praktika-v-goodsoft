package org.auth_server.web.servlets;

import org.auth_server.dto.UserWithRolesDTO;
import org.auth_server.entity.Role;
import org.auth_server.services.RoleService;
import org.auth_server.services.ServiceFactory;
import org.auth_server.services.UserRoleService;
import org.auth_server.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.auth_server.entity.enums.Path.JSP_PATH;
import static org.auth_server.entity.enums.Path.USERS_PAGE;

@WebServlet(name = "DispatcherServlet", urlPatterns = {"*.jhtml"})
public class DispatcherServlet extends HttpServlet {

    private final UserService userService = ServiceFactory.getInstance().getUserService();
    private final RoleService roleService = ServiceFactory.getInstance().getRoleService();
    private final UserRoleService userRoleService = ServiceFactory.getInstance().getUserRoleService();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        String jspPage = JSP_PATH.getPath() + path.replace(".jhtml", ".jsp");

        if (path.equals(USERS_PAGE.getPath() + ".jhtml")) {
            var users = userService.getAllUsers();

            var usersWithRoles = users.stream()
                    .map(user -> new UserWithRolesDTO(
                            user,
                            userRoleService.findRolesByUserId(user.getUserId())
                    ))
                    .toList();
            req.setAttribute("users", usersWithRoles);
        }

        if (path.equals("/edit-user.jhtml")) {
            String login = req.getParameter("login");
            var user = userService.getUserByLogin(login);
            req.setAttribute("user", user);
            req.setAttribute("roles", roleService.findAllRoles());

            var userRoles = userRoleService.findRolesByUserId(user.getUserId())
                    .stream()
                    .map(Role::getName)
                    .toList();

            req.setAttribute("userRoles", userRoles);
        }

        if (path.equals("/add-user.jhtml")) {
            req.setAttribute("roles", roleService.findAllRoles());
            req.setAttribute("add", true);
            req.getRequestDispatcher(JSP_PATH.getPath() + "/edit-user.jsp").forward(req, resp);
            return;
        }

        req.getRequestDispatcher(jspPage).forward(req, resp);
    }
}
