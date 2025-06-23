package org.auth_server.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/welcome")
public class WelcomeController {
    @GetMapping
    public String showWelcomePage(Model model) {
        model.addAttribute("currentPath", "/welcome");
        return "/welcome";
    }
}
