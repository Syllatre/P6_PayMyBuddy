package com.application.paymybuddy.repository;


import com.application.paymybuddy.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User save(User user);

    public User findByEmail(String email);

    @Query("SELECT CASE "
            + "WHEN COUNT(u) > 0 THEN true"
            + " ELSE false END "
            + "FROM User u "
            + "WHERE u.email = :email")
    public Boolean existsByEmail(@Param("email") String email);


    @Query("SELECT CASE "
            + "WHEN COUNT(u) > 0 THEN true"
            + " ELSE false END "
            + "FROM User u "
            + "WHERE u.userName = :userName")
    public Boolean existsByUsername(@Param("userName") String userName);



    @Query(value =
            "SELECT * "
                    + "FROM user u INNER JOIN user_connection ucon "
                    + "ON u.user_id = ucon.user_destination_id "
                    + "WHERE ucon.user_source_id = :id",
            nativeQuery = true)
    public Page<User> findConnectionById(@Param("id") Long id, Pageable pageRequest);
}

//SELECT * FROM user INNER JOIN user_connection ON user_id = user_destination_id WHERE user_source_id = 1;
