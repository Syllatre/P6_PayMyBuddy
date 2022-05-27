package com.application.paymybuddy.controller;

import com.application.paymybuddy.model.UserTransaction;
import com.application.paymybuddy.service.UserTransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@AllArgsConstructor
@Controller
public class UserTransactionController {

    private UserTransactionService userTransactionService;

    @GetMapping("/usertransactions")
    public String userTransactions(Model model,
                                   @RequestParam(required = false, value = "pageNumber", defaultValue = "1") int pageNumber,
                                   @RequestParam(required = false, value = "size",defaultValue = "5") int size) {
        model.addAttribute("userTransactions", userTransactionService.getPage(pageNumber,size));

        return "usertransactions";
    }


}
