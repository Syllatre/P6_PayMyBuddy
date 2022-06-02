package com.application.paymybuddy.repository;

import com.application.paymybuddy.model.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransfertRepository extends JpaRepository<UserTransaction,Long> {
}