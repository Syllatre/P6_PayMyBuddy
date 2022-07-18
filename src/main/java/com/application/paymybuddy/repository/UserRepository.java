package com.application.paymybuddy.repository;


import com.application.paymybuddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User save(User user);

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

    User findByEmail(String email);
}
