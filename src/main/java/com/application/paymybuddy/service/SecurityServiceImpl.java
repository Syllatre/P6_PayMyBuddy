package com.application.paymybuddy.service;

import com.application.paymybuddy.model.User;
import com.application.paymybuddy.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private UserRepository userRepository;

    @Override
    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
