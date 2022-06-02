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

    private TransfertService transfertService;

    @GetMapping("/transfert")
    public String findPaginated (Model model,
                                 @RequestParam(name = "page",defaultValue ="1" ) int page) {
        int size = 5;
    Page<UserTransaction> pageTransfert = transfertService.findPaginated(page,size);
    List<UserTransaction> transfert = pageTransfert.getContent();
    model.addAttribute("transfert",transfert);
    model.addAttribute("pages",new int[pageTransfert.getTotalPages()]);
    model.addAttribute("currentPage",page);
        return "transfert";
    }
}
