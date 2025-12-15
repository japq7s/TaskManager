package com.project.TaskManager.Controllers.Web;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginController {

    @PostMapping("/login")
    public String valid_login(@RequestParam String login, @RequestParam String password) {
        if (login.equals("admin") && password.equals("admin")) {
            return "Zalogowano pomyślnie! Witaj admin.";
        } else {
            return "Błąd logowania: Zła nazwa użytkownika lub hasło.";
        }
    }
    @GetMapping("/register")
    public String valid_register(@RequestParam String username) {

            return "Zarejestrowano pomyślnie." + username;
    }
}
