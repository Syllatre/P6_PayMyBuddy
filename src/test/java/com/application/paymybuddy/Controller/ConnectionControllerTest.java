package com.application.paymybuddy.Controller;

import com.application.paymybuddy.ConfigTest.ConfigurationTest;
import com.application.paymybuddy.controller.ConnectionController;
import com.application.paymybuddy.model.User;
import com.application.paymybuddy.model.UserTransaction;
import com.application.paymybuddy.service.ConnectionService;
import com.application.paymybuddy.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ConnectionController.class)
@Import(ConfigurationTest.class)
public class ConnectionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private ConnectionService connectionService;

    User user1;
    User user2;
    User user3;
    Page<User> pageTransfert;
    List<User> userList;

    @BeforeEach
    void setup() {
        user1 = new User(1L, "firstname1", "lastname1", "userName1", "aimenjerbi@gmail.com", "password1", new BigDecimal(100),
                new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), true);

        user2 = new User(2L, "firstname2", "lastname2", "userName2", "lastname2@gmail.com", "password2", new BigDecimal(100),
                new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), true);
        user3 = new User(3L, "firstname3", "lastname3", "userName3", "lastname3@gmail.com", "password3", new BigDecimal(100),
                new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), true);

        User[] users = {user1,user2,user3};
        userList = Arrays.asList(users);
        Pageable pageable = PageRequest.of(1, 5);

        pageTransfert = new PageImpl<>(userList, pageable, 3);
    }
    @WithUserDetails("aimenjerbi@gmail.com") //user from SpringSecurityWebTestConfig.class
    @Test
    void GetConnectionPage_shouldSucceed() throws Exception {
        //ARRANGE:
        when(userService.getCurrentUser()).thenReturn(user1);
        when(connectionService.getAllConnectionByCurrentUser(1, 5)).thenReturn(pageTransfert);

        //ACT+ASSERT:
        mockMvc.perform(get("/user/connection"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("connection"))
                .andExpect(model().size(3))
                .andExpect(model().attributeExists("connection"))
                .andExpect(model().attributeExists("pages"))
                .andExpect(model().attributeExists("currentPage"));
    }

    @WithUserDetails("aimenjerbi@gmail.com")
            @Test
    void PostConnectionPage_shouldSucceed() throws Exception {
        //ARRANGE:
        when(userService.getCurrentUser()).thenReturn(user1);
        when(userService.existsByEmail("lastname2@gmail.com")).thenReturn(true);
        when(userService.findByEmail("lastname2@gmail.com")).thenReturn(user2);
        when(connectionService.getAllConnectionByCurrentUser(1, 5)).thenReturn(pageTransfert);

        mockMvc.perform(post("/user/connection")
                        .param("email", "lastname2@gmail.com")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/connection?success"))
                .andExpect(redirectedUrl("/user/connection?success"));
    }

    @WithUserDetails("aimenjerbi@gmail.com")
    @Test
    void PostConnectionWithConnectionNoExist() throws Exception {
        //ARRANGE:
        when(userService.getCurrentUser()).thenReturn(user1);
        when(userService.existsByEmail("lastname2@gmail.com")).thenReturn(false);
        when(userService.findByEmail("lastname2@gmail.com")).thenReturn(user2);
        when(connectionService.getAllConnectionByCurrentUser(1, 5)).thenReturn(pageTransfert);

        mockMvc.perform(post("/user/connection")
                        .param("email", "lastname2@gmail.com")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("connection"))
                .andExpect(model().attribute("connection", userList))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attribute("error", "Email inconnu"));
    }

    @WithUserDetails("aimenjerbi@gmail.com")
    @Test
    void PostConnectionDeleteUser_shouldSucceed() throws Exception {
        //ARRANGE:
        user1.getConnections().add(user2);
        user1.getConnections().add(user3);
        when(userService.getCurrentUser()).thenReturn(user1);

        mockMvc.perform(post("/user/connectionDelete")
                        .param("id", "2")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/connection"))
                .andExpect(model().size(0))
        ;

        assertFalse(user1.getConnections().contains(user2));
        assertTrue(user1.getConnections().contains(user3));
    }
}
