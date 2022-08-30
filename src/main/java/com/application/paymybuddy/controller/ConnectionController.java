package com.application.paymybuddy.controller;

import com.application.paymybuddy.model.User;
import com.application.paymybuddy.service.ConnectionService;
import com.application.paymybuddy.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@AllArgsConstructor
public class ConnectionController {

    private UserService userService;

    private ConnectionService connectionService;

    @GetMapping("/user/connection")
    public String findPaginated(Model model, @RequestParam(name = "page", required = false, defaultValue = "1") int page) {
        int size = 5;
        Page<User> pageConnection = connectionService.getAllConnectionByCurrentUser(page, size);
        List<User> connection = pageConnection.getContent();
//        connection.forEach(System.out::println);
        model.addAttribute("connection", connection);
        model.addAttribute("pages", new int[pageConnection.getTotalPages()]);
        model.addAttribute("currentPage", page);
        User user = userService.getCurrentUser();
        return "connection";

    }

    @PostMapping("/user/connection")
    public String postConnection(@RequestParam String email,@RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                 Model model) {
        int size = 5;
        Page<User> pageConnection = connectionService.getAllConnectionByCurrentUser(page, size);
        List<User> connection = pageConnection.getContent();
        User user = userService.getCurrentUser();
        if (!userService.existsByEmail(email)) {
            model.addAttribute("error", "Email inconnu");
            model.addAttribute("connection", connection);
            return "connection";
        }
        if (user.getEmail().equalsIgnoreCase(email)) {
            model.addAttribute("error", "Vous ne pouvez pas vous rajouter");
            model.addAttribute("connection", connection);
            return "connection";
        }
        User newConnection = userService.findByEmail(email);
        user.getConnections().add(newConnection);
        userService.save(user);

        return "redirect:/user/connection?success";
    }

    @PostMapping("/user/connectionDelete")
    public String connectionDelete(@RequestParam Long id) {
        User user = userService.getCurrentUser();
        user.getConnections().removeIf(connection -> connection.getUserId().equals(id));
        userService.save(user);
        return "redirect:/user/connection";
    }
}
