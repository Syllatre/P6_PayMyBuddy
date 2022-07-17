package com.application.paymybuddy.service;

import com.application.paymybuddy.model.User;
import com.application.paymybuddy.model.UserTransaction;
import com.application.paymybuddy.repository.TransfertRepository;
import com.application.paymybuddy.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;

@AllArgsConstructor
@Service
public class TransfertService {

    private TransfertRepository transfertRepository;

    private UserService userService;

    LocalDateTimeService localDateTimeService;

    public Page<UserTransaction> findPaginated(int pageNumber, int size) {
        Pageable pageable = PageRequest.of(pageNumber -1,size);
        User getCurrentUser = userService.getCurrentUser();
        return transfertRepository.findUserTransactionByUserId(getCurrentUser.getUserId(), pageable);
    }

    public BigDecimal majUserSourceBalance(User user, BigDecimal amount) {

        BigDecimal userSourceBalance = user.getBalance();
        userSourceBalance.subtract(amount);
        user.setBalance(userSourceBalance);
        userService.save(user);
        return userSourceBalance;

    }

    public BigDecimal majUserDestinationBalance(User user, BigDecimal amount) {

        BigDecimal fees = new BigDecimal(1.0);
        BigDecimal userDestinationBalance = user.getBalance().subtract(fees);
        userDestinationBalance.add(amount);
        user.setBalance(userDestinationBalance);
        userService.save(user);
        return userDestinationBalance;

    }
    public UserTransaction create (UserTransaction userTransaction){
        userTransaction.setFees(new BigDecimal(1));
        userTransaction.setDateUserTransaction(localDateTimeService.now());
        return transfertRepository.save(userTransaction);
    }
}
