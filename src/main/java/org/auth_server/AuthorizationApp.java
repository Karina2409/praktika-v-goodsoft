package org.auth_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class AuthorizationApp {
    public static void main(String[] args) {
        SpringApplication.run(AuthorizationApp.class, args);
    }
}
