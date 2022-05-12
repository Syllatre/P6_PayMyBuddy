package com.application.paymybuddy.controller;

import com.application.paymybuddy.model.DTO.UserFormDTO;
import com.application.paymybuddy.model.User;
import com.application.paymybuddy.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@AllArgsConstructor
public class RegisterController {


    private UserService userService;

    private ModelMapper modelMapper;

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("userForm",new UserFormDTO());
        return "register";
    }

    @PostMapping("/save")
    public String addUser(@Valid @ModelAttribute("userForm") UserFormDTO userFormDTO, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {return "register";}
        log.debug("Creating user {}", userFormDTO);
        if (!userService.findByEmail(userFormDTO.getEmail()).isEmpty()) {
            bindingResult.rejectValue("email", "", "Cet email est deja existant");
            return "register";
        }
        User user = convertToEntity(userFormDTO);
        userService.createUser(user);
        return "redirect:/login";
    }

    private User convertToEntity(UserFormDTO userFormDTO) {
        User user = modelMapper.map(userFormDTO, User.class);
        return user;
    }
}

