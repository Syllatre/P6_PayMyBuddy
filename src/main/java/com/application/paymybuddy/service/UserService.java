package com.application.paymybuddy.service;

import com.application.paymybuddy.model.User;
import com.application.paymybuddy.repository.UserRepository;
import lombok.AllArgsConstructor;
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

    public User createUser(User user) {
        user.setBalance(new BigDecimal(0));
        return userRepository.save(user);}

    public Optional<User> findByEmail(String email){
        return  userRepository.findByEmail(email);
    }

   public Optional<User> findById(Long id){
        return userRepository.findById(id);
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
