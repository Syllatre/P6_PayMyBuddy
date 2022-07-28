package com.application.paymybuddy.repository;

import com.application.paymybuddy.model.User;
import com.application.paymybuddy.model.UserTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransfertRepository extends JpaRepository<UserTransaction, Long> {

    public Page<UserTransaction> findByUserSourceOrUserDestination(User userSource, User userDestination, Pageable pageRequest);
}
