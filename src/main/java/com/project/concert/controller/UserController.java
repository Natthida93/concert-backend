package com.project.concert.controller;

import com.project.concert.model.User;
import com.project.concert.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Accepts full User object from frontend via JSON
    @PostMapping("/login")
    public User loginOrRegister(@RequestBody User user) {
        return userService.loginOrRegister(user);
    }
}
