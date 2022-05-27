package com.application.paymybuddy.service;

import com.application.paymybuddy.model.UserTransaction;
import com.application.paymybuddy.page.Paged;
import com.application.paymybuddy.page.Paging;
import com.application.paymybuddy.repository.UserTransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserTransactionService {

    private UserTransactionRepository userTransactionRepository;

    public Paged<UserTransaction> getPage(int pageNumber, int size){
        PageRequest request = PageRequest.of(pageNumber - 1,size, Sort.by(Sort.Direction.ASC, "user_transaction_id"));
        Page<UserTransaction> userTransactionPage = userTransactionRepository.findAll(request);
        return new Paged<>(userTransactionPage, Paging.of(userTransactionPage.getTotalPages(),pageNumber,size));
    }
}
