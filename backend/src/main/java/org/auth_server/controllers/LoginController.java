package org.auth_server.controllers;

import lombok.RequiredArgsConstructor;
import org.auth_server.config.ApplicationConfig;
import org.auth_server.config.JwtService;
import org.auth_server.dto.LoginFormDTO;
import org.auth_server.entity.User;
import org.auth_server.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    private final JwtService jwtService;
    private final ApplicationConfig applicationConfig;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginFormDTO loginForm) {
        try {
            User user = userService.findUserByLogin(loginForm.getLogin());

            if (user == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "User with this login does not exist"));
            }

            if (!user.getPassword().equals(loginForm.getPassword())) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Invalid password"));
            }

            var userDetails = applicationConfig.userDetailsService().loadUserByUsername(user.getLogin());

            String token = jwtService.generateToken(userDetails);

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "user", user
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Authentication failed due to server error"));
        }
    }
}
