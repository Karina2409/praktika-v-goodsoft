package org.auth_server.web.servlets;

import org.auth_server.entity.User;
import org.auth_server.services.ServiceFactory;
import org.auth_server.services.UserRoleService;
import org.auth_server.services.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet (name = "EditUserServlet", urlPatterns = {"/doEdit-user.jhtml"})
public class EditUserServlet extends HttpServlet {

    private final UserService userService = ServiceFactory.getInstance().getUserService();
    private final UserRoleService userRoleService = ServiceFactory.getInstance().getUserRoleService();

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
