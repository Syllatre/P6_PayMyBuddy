package com.application.paymybuddy.controller;

import com.application.paymybuddy.model.UserTransaction;
import com.application.paymybuddy.service.UserTransactionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@AllArgsConstructor
@Controller
public class UserTransactionController {

    private UserTransactionService userTransactionService;

//    @GetMapping("/transfert")
//    public String userTransactions(Model model,
//                                   @RequestParam(required = false, value = "pageNumber", defaultValue = "1") int pageNumber,
//                                   @RequestParam(required = false, value = "size",defaultValue = "5") int size) {
//        model.addAttribute("userTransactions", userTransactionService.getPage(pageNumber,size));
//
//        return "transfert";
//    }
    @GetMapping("/transfert")
    public String viewTransfertPage(Model model){
        findPaginated(1,"userDestination","asc",model);
        return "transfert";
    }

    @GetMapping("transfert/page/{pageNumber}")
    public String findPaginated(@PathVariable(value = "pageNumber") int pageNumber,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model){
        int pageSize =5;

        Page<UserTransaction> page = userTransactionService.findPaginated(pageNumber,pageSize,sortField,sortDir);
        List<UserTransaction> transactionList = page.getContent();
        model.addAttribute("currentPage",pageNumber);
        model.addAttribute("totalPages",page.getTotalPages());
        model  .addAttribute("totalItems",page.getTotalElements());

        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("reverseSortDir",sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("transactionList",transactionList);

        return "transfert";
    }

}

