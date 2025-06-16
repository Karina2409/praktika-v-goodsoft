package org.auth_server.web.servlets;

import org.auth_server.entity.User;
import org.auth_server.services.ServiceFactory;
import org.auth_server.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.auth_server.entity.enums.Path.*;

@WebServlet(name = "PasswordEditServlet", urlPatterns = {"/do-password-edit.jhtml"})
public class PasswordEditServlet extends HttpServlet {

    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + LOGIN_PAGE.getPath() + ".jhtml");
            return;
        }

        String oldPassword = req.getParameter("old-password");
        String newPassword = req.getParameter("new-password");
        User user = (User) session.getAttribute("user");

        boolean isChanged = userService.changeUserPassword(user.getLogin(), oldPassword, newPassword);

        if (isChanged) {
            user.setPassword(newPassword);
            session.setAttribute("user", user);
            req.setAttribute("message", "Password changed successfully");
        } else if (oldPassword.equals(newPassword)) {
            req.setAttribute("error", "Old password is equal to new password");
        } else {
            req.setAttribute("error", "Old password is incorrect");
        }

        req.getRequestDispatcher(JSP_PATH.getPath() + PASSWORDEDIT_PAGE.getPath() + ".jsp").forward(req, resp);
    }
}
