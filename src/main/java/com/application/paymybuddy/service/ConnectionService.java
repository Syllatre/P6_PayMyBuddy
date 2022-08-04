package com.application.paymybuddy.service;

import com.application.paymybuddy.model.User;
import com.application.paymybuddy.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConnectionService {

    private UserService userService;

    private UserRepository userRepository;

    public Page<User> getAllConnectionByCurrentUser(int pageNumber, int size) {
        Pageable pageable = PageRequest.of(pageNumber - 1, size);
        User getCurrentUser = userService.getCurrentUser();
        return userRepository.findConnectionById(getCurrentUser.getUserId(), pageable);
    }
}
