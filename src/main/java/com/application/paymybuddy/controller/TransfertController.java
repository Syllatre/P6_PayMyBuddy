package com.application.paymybuddy.controller;

import com.application.paymybuddy.model.DTO.UserTransactionDTO;
import com.application.paymybuddy.model.User;
import com.application.paymybuddy.model.UserTransaction;
import com.application.paymybuddy.service.TransfertService;
import com.application.paymybuddy.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.AbstractSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@Controller
public class TransfertController {

    private TransfertService transfertService;

    private UserService userService;


    @GetMapping("/user/transfert")
    public String findPaginated(Model model,
                                @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                @AuthenticationPrincipal UserDetails userDetails) {
        int size = 5;
        Page<UserTransaction> pageTransfert = transfertService.findPaginated(page, size);
        List<UserTransaction> transfert = pageTransfert.getContent();
        UserTransactionDTO userTransactionDTO = new UserTransactionDTO();
        model.addAttribute("transfert", transfert);
        model.addAttribute("pages", new int[pageTransfert.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("userTransactionDTO", userTransactionDTO);
        User user = userService.findByEmail(userDetails.getUsername());
        model.addAttribute("user", user);
        return "transfert";

    }

    @PostMapping("/user/transfert")
    public String postTransfert(@Valid @ModelAttribute("userTransactionDTO") UserTransactionDTO userTransactionDTO,
                                BindingResult bindingResult,
                                Model model) {
        User userSource = userService.getCurrentUser();
        model.addAttribute("user", userSource);

        User userDestination = userService.findById(userSource.getUserId()).get();

        return "redirect:/user/transaction";
    }
}
