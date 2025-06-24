package org.auth_server.config;

import org.auth_server.entity.User;
import org.auth_server.services.UserRoleService;
import org.auth_server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @ModelAttribute
    public void addGlobalAttributes(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            String login = userDetails.getUsername();
            User user = userService.findUserByLogin(login);
            model.addAttribute("loginUser", user);

            boolean isAdmin = userRoleService.findRolesByUser(user.getUserId()).stream()
                    .anyMatch(role -> role.getName().equals("Администратор"));
            model.addAttribute("isAdmin", isAdmin);
        }
    }
}
