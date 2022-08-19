package com.application.paymybuddy.service;

import com.application.paymybuddy.model.BankTransaction;
import com.application.paymybuddy.model.User;
import com.application.paymybuddy.repository.BankTransactionRepository;
import com.application.paymybuddy.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class BankTransactionService {
    private UserService userService;

    private UserRepository userRepository;

    private BankTransactionRepository bankTransactionRepository;


    public Page<BankTransaction> findPaginated(int pageNumber, int size) {
        Pageable pageable = PageRequest.of(pageNumber - 1, size);
        User getCurrentUser = userService.getCurrentUser();
        return bankTransactionRepository.findByUser(getCurrentUser, pageable);
    }


    public BankTransaction getTransaction (BankTransaction bankTransaction){
        log.debug("Calling create(BankTransaction BankTransaction)");

        // creation de l'objet transaction
        User user = userService.getCurrentUser();
        bankTransaction.setUser(user);
        bankTransaction.setDatetime(LocalDateTime.now());
        bankTransactionRepository.save(bankTransaction);
        log.debug("transaction save");

        //Mis à jour du solde
        BigDecimal newBalance = user.getBalance().add(bankTransaction.getAmount());
        user.setBalance(newBalance);

        userRepository.save(user);
        log.debug("update balance of current user");


        return bankTransaction;
    }
}
