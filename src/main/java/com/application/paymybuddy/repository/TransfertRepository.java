package com.application.paymybuddy.repository;

import com.application.paymybuddy.model.UserTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransfertRepository extends JpaRepository<UserTransaction,Long> {
//    @Query(value =
//            "SELECT * FROM user_transaction  WHERE user_source_id = :usersourceid OR user_destination_id = :usersourceid"
//            , nativeQuery = true)
//    public Page<UserTransaction> findUserTransactionByUserId(@Param("usersourceid") Long userSourceId, Pageable pageRequest);
    public Page<UserTransaction> findAll(Pageable pageable);
}
