package org.auth_server.services.impl;

import org.auth_server.entity.User;
import org.auth_server.services.UserRoleService;
import org.auth_server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        System.out.println(login);
        User user = userService.findUserByLogin(login);
        System.out.println(user);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        var roles = userRoleService.findRolesByUser(user.getUserId());
        var authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();

        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                "{noop}" + user.getPassword(),
                authorities
        );
    }
}
