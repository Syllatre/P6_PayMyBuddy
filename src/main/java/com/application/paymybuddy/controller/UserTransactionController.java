package com.application.paymybuddy.controller;

import com.application.paymybuddy.model.UserTransaction;
import com.application.paymybuddy.service.UserTransactionService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@AllArgsConstructor
@Controller
public class UserTransactionController {

    private UserTransactionService userTransactionService;

    @GetMapping(path = "/index")
    public String userTransactions(Model model){
        UserTransaction userTransaction = new UserTransaction();
        model.addAttribute("userTransaction", userTransaction);
        return "userTransactions";
    }


}
