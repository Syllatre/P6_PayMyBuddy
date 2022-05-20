package com.application.paymybuddy.service;

import com.application.paymybuddy.model.User;
import com.application.paymybuddy.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;


@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;

    public User createUser(User user) {
        user.setBalance(new BigDecimal(0));
        return userRepository.save(user);}

    public Optional<User> findByEmail(String email){
        return  userRepository.findByEmail(email);
    }

    public User validLogin(String email, String password){
        return userRepository.findByEmailAndPassword(email,password);
    }
}
