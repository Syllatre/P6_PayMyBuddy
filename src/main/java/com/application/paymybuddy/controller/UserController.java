package com.application.paymybuddy.controller;

import com.application.paymybuddy.model.User;
import com.application.paymybuddy.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;


@Controller
@AllArgsConstructor
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user",new User());
        return "register";
    }



    @PostMapping("/save")
    public String addUser(Model model, @Valid User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "register";
        logger.debug("Creating user {}", user);
        userService.createUser(user);
        return "register";
    }
}
