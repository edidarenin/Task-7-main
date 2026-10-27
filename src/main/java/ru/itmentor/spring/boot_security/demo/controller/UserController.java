package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmentor.spring.boot_security.demo.model.User;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public User getCurrentUser(@AuthenticationPrincipal User user) {

        return user;
    }
}