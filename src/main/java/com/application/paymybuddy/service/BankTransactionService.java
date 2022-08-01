package com.application.paymybuddy.service;

import com.application.paymybuddy.model.BankTransaction;
import com.application.paymybuddy.model.User;
import com.application.paymybuddy.model.UserTransaction;
import com.application.paymybuddy.repository.BankTransactionRepository;
import com.application.paymybuddy.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class BankTransactionService {
    private UserService userService;

    private UserRepository userRepository;

    private BankTransactionRepository bankTransactionRepository;

    private LocalDateTimeService localDateTimeService;

    public Page<BankTransaction> findPaginated(int pageNumber, int size) {
        Pageable pageable = PageRequest.of(pageNumber - 1, size);
        User getCurrentUser = userService.getCurrentUser();
        return bankTransactionRepository.findByUser(getCurrentUser, pageable);
    }

    public BankTransaction getTransaction (BankTransaction bankTransaction){

        // creation de l'objet transaction
        bankTransaction.setUser(userService.getCurrentUser());
        bankTransaction.setDatetime(localDateTimeService.now());
        bankTransactionRepository.save(bankTransaction);
        User user = userService.getCurrentUser();

        //Mis à jour du solde
        BigDecimal newBalance = user.getBalance().add(bankTransaction.getAmount());
        user.setBalance(newBalance);

        userRepository.save(user);


        return bankTransaction;
    }
}
