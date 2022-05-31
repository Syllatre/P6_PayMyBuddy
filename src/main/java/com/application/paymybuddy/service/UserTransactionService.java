package com.application.paymybuddy.service;

import com.application.paymybuddy.model.UserTransaction;
import com.application.paymybuddy.page.Paged;
import com.application.paymybuddy.page.Paging;
import com.application.paymybuddy.repository.UserTransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserTransactionService {

    private UserTransactionRepository userTransactionRepository;

    public List<UserTransaction> getAllUserTransactions(){
        return userTransactionRepository.findAll();
    }

    public Page<UserTransaction> findPaginated(int pageNumber,int size, String sortField, String sortDirection){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNumber -1,size, sort);
        return userTransactionRepository.findAll(pageable);
    }
}
