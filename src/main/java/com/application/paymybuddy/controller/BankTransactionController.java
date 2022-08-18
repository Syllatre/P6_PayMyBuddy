package com.application.paymybuddy.controller;

import com.application.paymybuddy.model.BankTransaction;
import com.application.paymybuddy.model.DTO.BankTransactionDTO;
import com.application.paymybuddy.model.DTO.UserTransactionDTO;
import com.application.paymybuddy.model.User;
import com.application.paymybuddy.service.BankTransactionService;
import com.application.paymybuddy.service.TransfertService;
import com.application.paymybuddy.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@AllArgsConstructor
public class BankTransactionController {

    private BankTransactionService bankTransactionService;

    private UserService userService;

    private ModelMapper modelMapper;


    @GetMapping("/user/bank")
    public String findPaginated(@RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                Model model) {
        int size = 5;
        User currentUser = userService.getCurrentUser();
        Page<BankTransaction> pageTransfert = bankTransactionService.findPaginated(page, size);
        List<BankTransaction> transfert = pageTransfert.getContent();
        model.addAttribute("transfert", transfert);
        model.addAttribute("pages", new int[pageTransfert.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("currentUser", currentUser);

        BankTransactionDTO bankTransactionDTO = new BankTransactionDTO();
        model.addAttribute("bankTransactionDTO", bankTransactionDTO);
        return "bank";

    }

    @Transactional
    @PostMapping("/user/bank")
    public String postTransfert(@Valid @ModelAttribute("bankTransactionDTO")BankTransactionDTO bankTransactionDTO,
                                BindingResult bindingResult,
                                @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                Model model) {
        User currentUser = userService.getCurrentUser();
        int size = 5;
        Page<BankTransaction> pageTransfert = bankTransactionService.findPaginated(page, size);
        List<BankTransaction> transfert = pageTransfert.getContent();
        model.addAttribute("transfert", transfert);
        model.addAttribute("pages", new int[pageTransfert.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("currentUser", currentUser);

        if (bindingResult.hasErrors()) {
            return "bank";
        }


        BankTransaction bankTransaction = convertToEntity(bankTransactionDTO);

        bankTransactionService.getTransaction(bankTransaction);

        return "redirect:/user/bank?success";

    }

    private BankTransaction convertToEntity(BankTransactionDTO bankTransactionDTO) {

        //Auto-mapping for same name attributes
        BankTransaction bankTransaction = modelMapper.map(bankTransactionDTO, BankTransaction.class);
        //userDestinationId is mapped automatically by modelmapper to bankTransactionId which is bad, reset to null:
        bankTransaction.setBankTransactionId(null);

        return bankTransaction;
    }
}
