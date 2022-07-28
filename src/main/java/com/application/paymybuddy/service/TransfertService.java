package com.application.paymybuddy.service;

import com.application.paymybuddy.model.User;
import com.application.paymybuddy.model.UserTransaction;
import com.application.paymybuddy.repository.TransfertRepository;
import com.application.paymybuddy.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@AllArgsConstructor
@Service
public class TransfertService {

    private TransfertRepository transfertRepository;
    private UserService userService;
    private LocalDateTimeService localDateTimeService;
    private UserRepository userRepository;

    public Page<UserTransaction> findPaginated(int pageNumber, int size) {
        Pageable pageable = PageRequest.of(pageNumber - 1, size);
        User getCurrentUser = userService.getCurrentUser();
        return transfertRepository.findByUserSourceOrUserDestination(getCurrentUser,getCurrentUser,pageable);
    }

    public BigDecimal majUserSourceBalance(User user, BigDecimal amount) {
        User userDataBase = userRepository.findByEmail(user.getEmail());
        BigDecimal userSourceBalance = userDataBase.getBalance();
        userSourceBalance.subtract(amount);
        userDataBase.setBalance(userSourceBalance);
        userService.save(userDataBase);
        return userSourceBalance;

    }

    public BigDecimal majUserDestinationBalance(User user, BigDecimal amount) {
        User userDataBase = userRepository.findByEmail(user.getEmail());
        BigDecimal fees = new BigDecimal(1.0);
        BigDecimal userDestinationBalance = userDataBase.getBalance().subtract(fees);
        userDestinationBalance.add(amount);
        userDataBase.setBalance(userDestinationBalance);
        userService.save(userDataBase);
        return userDestinationBalance;

    }
    public UserTransaction getTransaction (UserTransaction userTransaction){

        // creation de l'objet transaction
        userTransaction.setUserSource(userService.getCurrentUser());
        userTransaction.setFees(new BigDecimal(1));
        userTransaction.setDateUserTransaction(localDateTimeService.now());
        transfertRepository.save(userTransaction);


        //Mis à jour du solde
        User userSource = userRepository.findByEmail(userTransaction.getUserSource().getEmail());
        User userDestination = userRepository.findByEmail(userTransaction.getUserDestination().getEmail());
        userSource.setBalance(userSource.getBalance().subtract(userTransaction.getAmount()).subtract(userTransaction.getFees()));
        userRepository.save(userSource);
        userDestination.setBalance(userDestination.getBalance().add(userTransaction.getAmount()));
        userRepository.save(userDestination);

        return userTransaction;
    }

}
