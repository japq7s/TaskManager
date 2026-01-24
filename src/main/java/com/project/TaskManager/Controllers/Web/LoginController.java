package com.project.TaskManager.Controllers.Web;
import com.project.TaskManager.UserManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginController {
    private final UserManager userMgr;

    public LoginController(UserManager userMgr) {
        this.userMgr = userMgr;
    }
    @PostMapping("/login")
    public String valid_login(@RequestParam String login, @RequestParam String password) {
        UserManager userMgr = new UserManager();
        int userId = userMgr.login(login, password);
        if (userId != -1) {
            return "Zalogowano pomyślnie! Witaj " + login +
                    "\nTwój userId: " + userId;
        } else {
            return "Błąd logowania: Zła nazwa użytkownika lub hasło.";
        }
    }
    @PostMapping("/register")
    public String valid_register(@RequestParam String username, @RequestParam String password) {
        boolean success = userMgr.register(username, password);

        if (success) {
            return "Zarejestrowano pomyślnie: " + username;
        } else {
            return "Błąd rejestracji ";
        }
    }
}
