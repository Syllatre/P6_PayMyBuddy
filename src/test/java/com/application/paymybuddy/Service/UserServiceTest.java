package com.application.paymybuddy.Service;

import com.application.paymybuddy.ConfigTest.ConfigurationTest;
import com.application.paymybuddy.model.Role;
import com.application.paymybuddy.model.User;
import com.application.paymybuddy.repository.RoleRepository;
import com.application.paymybuddy.repository.UserRepository;
import com.application.paymybuddy.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;

    @Mock
    static BCryptPasswordEncoder encoder;


    User user;
    @BeforeEach
    void init(){
        user = new User(1L, "toto", "titi", "akira", "akira@gmail.com", "1234",null , new HashSet<>(),
                new HashSet<>(), new HashSet<>(), new HashSet<>(), false);
    }

    @Test
    void createUserTest(){
        User user1 = new User(null, "toto", "titi", "akira", "akira@gmail.com", "1234",new BigDecimal(0.00) , new HashSet<>(),
                new HashSet<>(), new HashSet<>(), new HashSet<>(), true);

        User userExpected = new User(null, "toto", "titi", "akira", "akira@gmail.com", "1234encrypted",new BigDecimal(0.00) , new HashSet<>(),
                new HashSet<>(), new HashSet<>(), new HashSet<>(), true);
        HashSet<Role> hashsetRole = new HashSet<>();
        hashsetRole.add(new Role(1L,"USER"));
        userExpected.setRoles(hashsetRole);

        when(roleRepository.findByRole("USER")).thenReturn(new Role(1L,"USER"));

        userService.createUser(user1);

        assertEquals(userExpected.getBalance(),user1.getBalance());
        assertEquals(userExpected.getFirstName(),user1.getFirstName());
    }


    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    void getCurrentUserDetailsUserNameTest() {
        String email=userService.getCurrentUserDetailsUserName();
    assertEquals(email,"admin");
    }



    @Test
    void findByEmailTest() {
        String email = "akira@gmail.com";
        when(userRepository.findByEmail(email)).thenReturn(user);

         User userResult =userService.findByEmail(email);

        assertEquals(userResult,user);
    }

    @Test
    void findByIdTest() {
        Optional<User> optUser = Optional.of(user);
        when(userRepository.findById(1L)).thenReturn(optUser);

        User resultUser = userService.findById(1L);

        assertEquals(optUser.get(),resultUser);
    }

    @Test
    void existsByEmailTest() {
        String email = "akira@gmail.com";
        when(userRepository.existsByEmail(email)).thenReturn(true);

        Boolean result = userService.existsByEmail(email);
        assertTrue(result);
    }

    @Test
    void existsByUserNameTest() {
        String userName = "akira";
        when(userRepository.existsByUsername(userName)).thenReturn(true);

        Boolean result = userService.existsByUserName(userName);
        assertTrue(result);
    }

    @Test
    void saveTest(){
        User user1 = new User(null, "toto", "titi", "akira", "akira@gmail.com", "1234",new BigDecimal(0.00) , new HashSet<>(),
                new HashSet<>(), new HashSet<>(), new HashSet<>(), true);

        User userExpected = new User(null, "toto", "titi", "akira", "akira@gmail.com", "1234",new BigDecimal(0.00) , new HashSet<>(),
                new HashSet<>(), new HashSet<>(), new HashSet<>(), true);

        userService.save(user1);

        assertEquals(userExpected.getUserId(),user1.getUserId());
        assertEquals(userExpected.getFirstName(),user1.getFirstName());
        assertEquals(userExpected.getLastName(),user1.getLastName());
        assertEquals(userExpected.getBalance(),user1.getBalance());
    }

//@Test
//    void getCurrentUserTest() {
//        User user10 = new User(null, "toto", "titi", "akira", "akira@gmail.com", "1234",new BigDecimal(0.00) , new HashSet<>(),
//                new HashSet<>(), new HashSet<>(), new HashSet<>(), true);
//        when(userRepository.findByEmail("akira@gmail.com")).thenReturn(user10);
//        User userResponse = userService.getCurrentUser();
//        assertEquals(userResponse.getUserName(),user10.getUserName());
//    }

//    @PreAuthorize("authenticated")
//    public String getMessage() {
//        Authentication authentication = SecurityContextHolder.getContext()
//                .getAuthentication();
//        return "Hello " + authentication;
//    }
//    @Test
//    @WithMockUser(username="admin",roles={"USER","ADMIN"})
//    public void getMessageWithMockUserCustomUser() {
//        String message = getMessage();
//    assertEquals("Hello admin",getMessage());
//    }
}
