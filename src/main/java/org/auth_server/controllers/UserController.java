package org.auth_server.controllers;

import jakarta.validation.Valid;
import org.auth_server.dto.UserWithRolesDTO;
import org.auth_server.entity.Role;
import org.auth_server.entity.User;
import org.auth_server.services.RoleService;
import org.auth_server.services.UserRoleService;
import org.auth_server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @GetMapping
    public String getUsers(Model model) {
        var users = userService.findAllUsers();
        var usersWithRoles = users.stream()
                .map(user -> new UserWithRolesDTO(
                        user,
                        userRoleService.findRolesByUserId(user.getUserId())
                ))
                .toList();
        model.addAttribute("users", usersWithRoles);
        return "/users";
    }

    @GetMapping("/add")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.findAllRoles());
        model.addAttribute("add", true);
        return "/edit-user";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") @Valid User user,
                          BindingResult bindingResult,
                          @RequestParam("roleIds") List<Integer> roleIds,
                          Model model) {
        if (user.getBirthday() != null) {
            int age = Period.between(user.getBirthday(), LocalDate.now()).getYears();
            if (age < 18) {
                bindingResult.rejectValue("birthday", "error.user", "Пользователь должен быть старше 18 лет");
            }
            user.setAge(age);
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleService.findAllRoles());
            model.addAttribute("add", true);
            return "/edit-user";
        }

        User newUser = userService.addUser(user);
        if (newUser == null) {
            model.addAttribute("error", "Пользователь с таким логином уже существует");
            model.addAttribute("roles", roleService.findAllRoles());
            model.addAttribute("add", true);
            return "/edit-user";
        }

        userRoleService.addRolesToUser(newUser.getUserId(), roleIds.stream().mapToInt(Integer::intValue).toArray());
        return "redirect:/users";
    }

    @GetMapping("/edit")
    public String showEditUserForm(@RequestParam("login") String login, Model model) {
        var user = userService.findUserByLogin(login);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.findAllRoles());
        var userRoles = userRoleService.findRolesByUserId(user.getUserId())
                .stream()
                .map(Role::getName)
                .toList();
        model.addAttribute("userRoles", userRoles);
        return "/edit-user";
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute("user") @Valid User user,
                           BindingResult bindingResult,
                           @RequestParam("roleIds") List<Integer> roleIds,
                           Model model) {
        if (user.getBirthday() != null) {
            int age = Period.between(user.getBirthday(), LocalDate.now()).getYears();
            if (age < 18) {
                bindingResult.rejectValue("birthday", "error.user", "Пользователь должен быть старше 18 лет");
            }
            user.setAge(age);
        }

        if (bindingResult.hasErrors()) {
            showEditUserForm(user.getLogin(), model);
        }
        User oldUser = userService.findUserById(user.getUserId());
        user.setLogin(oldUser.getLogin());
        user.setPassword(oldUser.getPassword());
        if (oldUser != null) {
            userService.updateUser(user);
            userRoleService.removeAllRolesFromUser(user.getUserId());
            userRoleService.addRolesToUser(user.getUserId(), roleIds.stream().mapToInt(Integer::intValue).toArray());
        }
        return "redirect:/users";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("login") String login) {
        userService.deleteUser(login);
        return "redirect:/users";
    }
}
