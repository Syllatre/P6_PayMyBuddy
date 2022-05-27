
package com.application.paymybuddy.controller;

import com.application.paymybuddy.model.DTO.LoginDTO;
import com.application.paymybuddy.model.User;
import com.application.paymybuddy.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Controller
@AllArgsConstructor
public class LoginController {

    private UserService userService;

    @GetMapping("/login")
    public String login(Model model){
        LoginDTO loginDTO = new LoginDTO();
        model.addAttribute("loginDTO",loginDTO);
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@Valid @ModelAttribute("loginDTO")LoginDTO loginDTO,
                            BindingResult bindingResult,
                            Model model){
        User userData = userService.validLogin(loginDTO.getEmail(),loginDTO.getPassword());
        if(bindingResult.hasErrors()){
            return "login";
        }

        if(!userData.getEmail().equalsIgnoreCase(loginDTO.getEmail())){
            bindingResult.rejectValue("password","","l'email est incorrect");
            return "login";
        }

        if(!userData.getPassword().equals(loginDTO.getPassword())){
            bindingResult.rejectValue("password","","le mot de passe est incorrect");
            return "login";
        }

        if(userData.getEmail().equalsIgnoreCase(loginDTO.getEmail()) && userData.getPassword().equals(loginDTO.getPassword())){
            return "redirect:/home";
        }
        else{
            return "login";
        }
    }
}
