package com.application.paymybuddy.controller;

import com.application.paymybuddy.model.UserTransaction;
import com.application.paymybuddy.repository.TransfertRepository;
import com.application.paymybuddy.service.TransfertService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@AllArgsConstructor
@Controller
public class TransfertController {

    private TransfertRepository transfertRepository;

    @GetMapping("/transfert")
    public String findPaginated (Model model,
                                 @RequestParam(name = "page",defaultValue ="0" ) int page,
                                 @RequestParam(name = "size",defaultValue = "5") int size) {
    Page<UserTransaction> transfert = transfertRepository.findAll(PageRequest.of(page,size));
    model.addAttribute("transfert",transfert.getContent());
    model.addAttribute("pages",new int[transfert.getTotalPages()]);
    model.addAttribute("currentPage",page);
        return "transfert";
    }
}
