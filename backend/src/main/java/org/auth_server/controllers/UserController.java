package org.auth_server.controllers;

import jakarta.validation.Valid;
import org.auth_server.dto.UserWithRolesDTO;
import org.auth_server.entity.Role;
import org.auth_server.entity.User;
import org.auth_server.services.RoleService;
import org.auth_server.services.UserRoleService;
import org.auth_server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @GetMapping
    public List<UserWithRolesDTO> getUsers() {
        var users = userService.findAllUsers();
        var usersWithRoles = users.stream()
                .map(user -> new UserWithRolesDTO(
                        user,
                        userRoleService.findRolesByUserId(user.getUserId())
                ))
                .toList();
        return usersWithRoles;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody UserWithRolesDTO requestUser) {
        //TODO: удалить расчет даты рождения на фронте
        //TODO: изменить передачу пользователя на бэк. чтобы роли отправлялись отдельно
        try {
            User user = requestUser.getUser();
            List<String> roleNames = requestUser.getRoles();

            if (userService.findUserByLogin(user.getLogin()) != null) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(Map.of("error", "Login already exists"));
            }
            user.setAge(Period.between(user.getBirthday(), LocalDate.now()).getYears());
            User newUser = userService.addUser(user);

            int[] roleIds = roleNames.stream()
                    .map(roleService::findRoleByName)
                    .filter(Objects::nonNull)
                    .map(role -> {
                        if (role == null) {
                            throw new IllegalArgumentException("Role not found");
                        }
                        return role.getId();
                    })
                    .mapToInt(Integer::intValue)
                    .toArray();

            userRoleService.addRolesToUser(newUser.getUserId(), roleIds);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(newUser);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "User creation failed"));
        }

    }

    @GetMapping("/user")
    public UserWithRolesDTO findUserByLogin(@RequestParam("login") String login) {
        User user = userService.findUserByLogin(login);
        List<String> userRoles = userRoleService.findRolesByUserId(user.getUserId());

        return new UserWithRolesDTO(user, userRoles);
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute("user") @Valid User user,
                           BindingResult bindingResult,
                           @RequestParam("roleIds") List<Integer> roleIds,
                           Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("roles", roleService.findAllRoles());
            var userRoles = userRoleService.findRolesByUserId(user.getUserId());
            model.addAttribute("userRoles", userRoles);
            model.addAttribute("currentPath", "/users/edit");
            return "edit-user";
        }
        User oldUser = userService.findUserById(user.getUserId());
        user.setLogin(oldUser.getLogin());
        user.setPassword(oldUser.getPassword());
        user.setAge(Period.between(user.getBirthday(), LocalDate.now()).getYears());
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
