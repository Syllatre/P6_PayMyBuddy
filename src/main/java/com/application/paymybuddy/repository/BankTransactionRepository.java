package com.application.paymybuddy.repository;

import com.application.paymybuddy.model.BankTransaction;
import com.application.paymybuddy.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {

    public Page<BankTransaction> findByUser(User user, Pageable pageRequest);
}

