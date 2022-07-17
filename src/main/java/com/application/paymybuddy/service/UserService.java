package com.application.paymybuddy.service;

import com.application.paymybuddy.model.Role;
import com.application.paymybuddy.model.User;
import com.application.paymybuddy.repository.RoleRepository;
import com.application.paymybuddy.repository.UserRepository;
import lombok.AllArgsConstructor;
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
        Role role = roleRepository.findByRole("USER");
        roles.add(role);
        user.setRoles(roles);
        user.setBalance(new BigDecimal(0));
        user.setPassword(encoder.encode(user.getPassword()));
        user.setActive(true);

        return userRepository.save(user);
    }

   public Optional<User> findById(Long id){
        return userRepository.findById(id);
   }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Boolean existsByUserName(String userName) {
        return userRepository.existsByUsername(userName);
    }

   public List<User> findAll(){
        return userRepository.findAll();
   }

   public Set<User> UserListWithNoConnection(){
        User user = userRepository.findById(2L).get();
        List <User> allUser = userRepository.findAll();
        Set<User> userConnection = user.getConnections();
        Set<User> userWithNoConnection = new HashSet<>();
       for(User allUsers : allUser){
            for (User userConnections : userConnection){
                if(userConnections.equals(allUsers)){
                    allUser.remove(userConnections);
                }
            }
        }
       for(User allUserFilter: allUser){
           userWithNoConnection.add(allUserFilter);
       }
        return userWithNoConnection;
   }
   public User save(User user){
        return userRepository.save(user);
   }
}
