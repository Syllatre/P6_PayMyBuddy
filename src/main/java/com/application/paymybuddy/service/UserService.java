package com.application.paymybuddy.service;

import com.application.paymybuddy.model.User;
import com.application.paymybuddy.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }
}
