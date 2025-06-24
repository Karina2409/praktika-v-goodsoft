package org.auth_server.controllers;

import org.auth_server.dto.LoginFormDTO;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String showLoginPage(Model model,
                                @RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null &&
                authentication.isAuthenticated() &&
                !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/welcome";
        }

        model.addAttribute("loginForm", new LoginFormDTO());
        model.addAttribute("currentPath", "/login");
        if (error != null) {
            model.addAttribute("error", "Неверные логин или пароль");
        }
        if (logout != null) {
            model.addAttribute("message", "Вы успешно вышли из системы");
        }
        return "login";
    }
}
