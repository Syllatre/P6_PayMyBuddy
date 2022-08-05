package com.application.paymybuddy.service;

import com.application.paymybuddy.model.Role;
import com.application.paymybuddy.model.User;
import com.application.paymybuddy.repository.RoleRepository;
import com.application.paymybuddy.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    public User createUser(User user) {

        String encryptedPassword = encoder.encode(user.getPassword());
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByRole("USER"));
        user.setRoles(roles);
        user.setBalance(new BigDecimal(0.00));
        user.setPassword(encryptedPassword);
        user.setActive(true);

        return userRepository.save(user);
    }

    public String getCurrentUserDetailsUserName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof org.springframework.security.core.userdetails.User) {
                return ((org.springframework.security.core.userdetails.User) principal).getUsername();
            }
        }
        return null;
    }
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getCurrentUser() {
        return findByEmail(getCurrentUserDetailsUserName());
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Boolean existsByUserName(String userName) {
        return userRepository.existsByUsername(userName);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
