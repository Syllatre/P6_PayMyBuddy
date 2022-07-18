package com.application.paymybuddy.repository;

import com.application.paymybuddy.model.UserTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTransactionRepository extends JpaRepository<UserTransaction,Long> {

    @Query(value =
            "SELECT * FROM user_transaction WHERE user_source_id = :userSourceId OR user_destination_id = :userSourceId "
            ,
            nativeQuery = true)
    public Page<UserTransaction> findUserTransactionByUserId(@Param("userSourceId") Long userSourceId, Pageable pageRequest);

}