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
    public String findPaginated(@RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                @AuthenticationPrincipal UserDetails userDetails,
                                Model model) {
        int size = 5;
        Page<UserTransaction> pageTransfert = transfertService.findPaginated(page, size);
        List<UserTransaction> transfert = pageTransfert.getContent();
        UserTransactionDTO userTransactionDTO = new UserTransactionDTO();
        model.addAttribute("transfert", transfert);
        model.addAttribute("pages", new int[pageTransfert.getTotalPages()]);
        model.addAttribute("currentPage", page);
        User user = userService.findByEmail(userDetails.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("userTransactionDTO",userTransactionDTO);
        return "transfert";

    }
    @Transactional
    @PostMapping("/user/transfert")
    public String postTransfert( @Valid @ModelAttribute("userTransactionDTO") UserTransactionDTO userTransactionDTO,
                                BindingResult bindingResult,
                                @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                Model model) {
        User userDestination = userService.findById(userTransactionDTO.getUserDestinationId());
        int size = 5;
        Page<UserTransaction> pageTransfert = transfertService.findPaginated(page, size);
        List<UserTransaction> transfert = pageTransfert.getContent();
        model.addAttribute("transfert", transfert);
        model.addAttribute("pages", new int[pageTransfert.getTotalPages()]);
        model.addAttribute("currentPage", page);
        User user =userService.getCurrentUser();
        model.addAttribute("user", user);
        if(bindingResult.hasErrors()) {return "transfert";}

    if (!user.getConnections().contains(userDestination)){
            log.debug("Failure: unknown buddy");
            bindingResult.rejectValue("userDestinationId", "userDestinationNotABuddy", "Veuillez selectionner un beneficaire");
            return "transfert";}

        int validation = user.getBalance().compareTo(userTransactionDTO.getAmount());

        if (validation < 0) {
            bindingResult.rejectValue("amount", "insufficientBalance", "Votre solde est insuffisant");
            return "transfert";
        }

        UserTransaction userTransaction = convertToEntity(userTransactionDTO, userDestination);

        transfertService.getTransaction(userTransaction);

        return "redirect:/user/transfert?success";

    }

    private UserTransaction convertToEntity(UserTransactionDTO userTransactionDTO, User userDestination) {

        log.debug("DTO object to Entity conversion");

        UserTransaction userTransaction = modelMapper.map(userTransactionDTO, UserTransaction.class);
        userTransaction.setUserTransactionId(null);
        userTransaction.setUserDestination(userDestination);

        return userTransaction;
    }
}