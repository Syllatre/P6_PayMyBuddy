package com.application.paymybuddy.controller;

import com.application.paymybuddy.model.DTO.UserTransactionDTO;
import com.application.paymybuddy.model.User;
import com.application.paymybuddy.model.UserTransaction;
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
@AllArgsConstructor
@Controller
public class TransfertController {

    private TransfertService transfertService;

    private UserService userService;

    private ModelMapper modelMapper;


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
    @Transactional
    @PostMapping("/user/transfert")
    public String postTransfert(@Valid @ModelAttribute("userTransactionDTO") UserTransactionDTO userTransactionDTO,
                                BindingResult bindingResult,Model model) {
        if (bindingResult.hasErrors()) {
            return "transfert";
        }
        User user = userService.getCurrentUser();
        User userDestination = userService.findById(userTransactionDTO.getUserDestinationId()).get();

        if (!user.getConnections().contains(userDestination)) {
            bindingResult.rejectValue("userDestinationId", "userDestinationNotABuddy", "Veuillez choisir un buddy");
            return "transfert";
        }

        int validation = user.getBalance().compareTo(userTransactionDTO.getAmount());

        if (validation == -1) {
            bindingResult.rejectValue("amount", "insufficientBalance", "Votre solde est insuffisant");
            return "transfert";
        }

        if (userTransactionDTO.getComments() == null || userTransactionDTO.getComments().length() < 1) {
            bindingResult.rejectValue("comment", "reasonRequired", "Veuillez renseigner le descriptif");
            return "transfert";
        }

        UserTransaction userTransaction = convertToEntity(userTransactionDTO, userDestination);

        transfertService.getTransaction(userTransaction);

        return "redirect:/user/transfert?success";

    }

    private UserTransaction convertToEntity(UserTransactionDTO userTransactionDTO, User userDestination) {

        log.debug("DTO object to Entity conversion");

        //Auto-mapping for same name attributes
        UserTransaction userTransaction = modelMapper.map(userTransactionDTO, UserTransaction.class);
        //userDestinationId is mapped automatically by modelmapper to userTransaction.id which is bad, reset to null:
        userTransaction.setUserTransactionId(null);
        //Mapping from DTO.id to Entity.User:
        userTransaction.setUserDestination(userDestination);

        return userTransaction;
    }
}
