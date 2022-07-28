package com.application.paymybuddy.service;

import com.application.paymybuddy.model.Role;
import com.application.paymybuddy.model.User;
import com.application.paymybuddy.repository.RoleRepository;
import com.application.paymybuddy.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    public User createUser(User user) {

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByRole("USER"));
        user.setRoles(roles);
        user.setBalance(new BigDecimal(0.00));
        user.setPassword(encoder.encode(user.getPassword()));
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

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Set<User> UserListWithNoConnection() {
        User user = getCurrentUser();
        List<User> allUser = userRepository.findAll();
        Set<User> userConnection = user.getConnections();
        Set<User> userWithNoConnection = new HashSet<>();
        for (User allUsers : allUser) {
            for (User userConnections : userConnection) {
                if (userConnections.equals(allUsers)) {
                    allUser.remove(userConnections);
                }
            }
        }
        for (User allUserFilter : allUser) {
            userWithNoConnection.add(allUserFilter);
        }
        return userWithNoConnection;
    }
    public User save(User user) {
        return userRepository.save(user);
    }
}
