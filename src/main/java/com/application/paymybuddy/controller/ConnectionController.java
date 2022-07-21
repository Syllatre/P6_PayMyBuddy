package com.application.paymybuddy.controller;

import com.application.paymybuddy.model.User;
import com.application.paymybuddy.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
@AllArgsConstructor
public class ConnectionController {

    private UserService userService;

        @GetMapping("/connection")
    public String getConnection(Model model){
    Set<User> userWithNoConnection = userService.UserListWithNoConnection();
    model.addAttribute("userConnection",userWithNoConnection);
    return "connection";
    }

    @PostMapping("/connection")
    public String postConnection(@ModelAttribute("userConnection") Long userConnection,
                                 Model model,
                                 BindingResult bindingResult){
        User user = userService.findById(2L).get();
        Set<User> connection = new HashSet<>();
        connection.add(userService.findById(userConnection).get());
        user.setConnections(connection);
        userService.save(user);
        return "connection";
    }
}
