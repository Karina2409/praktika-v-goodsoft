package org.auth_server.controllers;

import jakarta.servlet.http.HttpSession;
import org.auth_server.dto.LoginFormDTO;
import org.auth_server.entity.User;
import org.auth_server.services.UserRoleService;
import org.auth_server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @GetMapping
    public String showLoginPage(Model model) {
        model.addAttribute("loginForm", new LoginFormDTO());
        return "/login";
    }

    @PostMapping
    public String login(@RequestParam("login") String login,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {
        User newUser = userService.login(login, password);
        if (newUser != null) {
            session.setAttribute("user", newUser);

            var roles = userRoleService.findRolesByUserId(newUser.getUserId());
            session.setAttribute("roles", roles);

            for (var role : roles) {
                if ("Администратор".equals(role.getName())) {
                    session.setAttribute("isAdmin", true);
                    break;
                }
            }
            return "redirect:/welcome";
        } else {
            model.addAttribute("error", "Неверные логин или пароль");
            return "/login";
        }
    }
}
