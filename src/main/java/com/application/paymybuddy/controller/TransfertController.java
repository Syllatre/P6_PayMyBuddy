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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Controller
public class TransfertController {

    private TransfertService transfertService;

    private UserService userService;

    private ModelMapper modelMapper;


    @GetMapping("/transfert")
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

    @PostMapping("/transfert")
    public String postTransfert(@Valid @ModelAttribute("userTransactionDTO") UserTransactionDTO userTransactionDTO,
                                BindingResult bindingResult,
                                Model model) {
        User userSource = userService.getCurrentUser();
        model.addAttribute("user", userSource);

        User userDestination = userService.findById(userTransactionDTO.getUserDestinationId()).get();

        if (!userSource.getConnections().contains(userDestination)) {
            bindingResult.rejectValue("userDestinationId", "userDestinationNotABuddy", "Please select a buddy !");
            return "transfert";
        }

        UserTransaction userTransaction = convertToEntity(userTransactionDTO, userDestination);

        //cross-record validation : calculate user amount after transaction, UserAmountException thrown if amount is invalid
        BigDecimal userSourceAmountAfterTransaction;
        BigDecimal userDestinationAmountAfterTransaction;
//        try {
        userSourceAmountAfterTransaction = transfertService.majUserSourceBalance(userSource, userTransaction.getAmount());
        userDestinationAmountAfterTransaction = transfertService.majUserDestinationBalance(userDestination, userTransaction.getAmount());
//
//        } catch (UserAmountException e) {
//            logger.debug("UserAmountException");
//            bindingResult.rejectValue("amount", e.getErrorCode(), e.getDefaultMessage());
//            return "usertransaction";
//        }

        //update users amount:
        transfertService.majUserDestinationBalance(userDestination, userTransaction.getAmount());
        transfertService.majUserSourceBalance(userSource, userTransaction.getAmount());

        //create usertransaction:
        transfertService.create(userTransaction);

        //redirection do not use the current Model, it goes to GET /bantransaction
        return "redirect:/transfert";

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
