package org.auth_server.config;

import lombok.RequiredArgsConstructor;
import org.auth_server.entity.User;
import org.auth_server.services.UserRoleService;
import org.auth_server.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserService userService;
    private final UserRoleService userRoleService;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userService.findUserByLogin(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found: " + username);
            }
            var roleNames = userRoleService.findRolesByUserId(user.getUserId()).toArray(new String[0]);
            return org.springframework.security.core.userdetails.User.withUsername(user.getLogin()).password(user.getPassword()).authorities(roleNames).build();
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
