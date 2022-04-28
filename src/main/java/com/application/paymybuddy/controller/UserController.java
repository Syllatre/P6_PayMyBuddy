package com.application.paymybuddy.controller;

import com.application.paymybuddy.model.User;
import com.application.paymybuddy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/connexion")
    public User addUser(@Valid @RequestBody User user) {
        logger.debug("Creating user {}", user);
        return userService.createUser(user);
    }
}
