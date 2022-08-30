package com.application.paymybuddy.Controller;

import com.application.paymybuddy.ConfigTest.ConfigurationTest;
import com.application.paymybuddy.controller.TransfertController;
import com.application.paymybuddy.model.DTO.UserTransactionDTO;
import com.application.paymybuddy.model.User;
import com.application.paymybuddy.model.UserTransaction;
import com.application.paymybuddy.service.TransfertService;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransfertController.class)
@Import(ConfigurationTest.class)
public class TransfertControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    TransfertService transfertService;

    User user1;
    User user2;
    User user3;
    UserTransaction userTransaction1;
    UserTransaction userTransaction2;
    UserTransaction userTransaction3;
    Page<UserTransaction> pageTransfert;

    @BeforeEach
    void setup() {
        user1 = new User(1L, "firstname1", "lastname1", "userName1", "lastname1@gmail.com", "password1", new BigDecimal(100),
                new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), true);

        user2 = new User(2L, "firstname2", "lastname2", "userName2", "lastname2@gmail.com", "password2", new BigDecimal(100),
                new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), true);
        user3 = new User(3L, "firstname3", "lastname3", "userName3", "lastname3@gmail.com", "password3", new BigDecimal(100),
                new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), true);

        userTransaction1 = new UserTransaction(1L, user1, user2, LocalDateTime.of(2023, 01, 01, 00, 45),"comment1",
                new BigDecimal("100.10"), new BigDecimal("1.00"));

        userTransaction2 = new UserTransaction(2L, user1, user2, LocalDateTime.of(2023, 01, 01, 00, 45),"comment2",
                new BigDecimal("100.10"), new BigDecimal("1.00"));

        userTransaction3 = new UserTransaction(3L, user1, user2, LocalDateTime.of(2023, 01, 01, 00, 45),"comment3",
                new BigDecimal("100.10"), new BigDecimal("1.00"));


        UserTransaction[] userTransactionArray = {userTransaction1,userTransaction2,userTransaction3};
        List<UserTransaction> userTransactions = Arrays.asList(userTransactionArray);
        Pageable pageable = PageRequest.of(1, 5);

        pageTransfert = new PageImpl<>(userTransactions, pageable, 3);

    }


    @WithUserDetails("aimenjerbi@gmail.com")
    @Test
    void GetUserTransaction_shouldSucceed() throws Exception {
        when(userService.getCurrentUser()).thenReturn(user1);
        when(transfertService.findPaginated(1, 5)).thenReturn(pageTransfert);

        //ACT+ASSERT
        mockMvc.perform(get("/user/transfert"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("transfert"))
                .andExpect(model().size(5))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("userTransactionDTO"))
                .andExpect(model().attributeExists("transfert"))
                .andExpect(model().attributeExists("pages"))
                .andExpect(model().attributeExists("currentPage"));
    }

    @WithUserDetails("aimenjerbi@gmail.com")
    @Test
    void PostTransfert_SendMoneyShouldSucceedAndRedirected() throws Exception {
        user1.getConnections().add(user2);
        BigDecimal transactionAmount = new BigDecimal("33");
        Long userDestinationId = 2L;

        when(userService.getCurrentUser()).thenReturn(user1);
        when(userService.findById(2L)).thenReturn(user2);
        when(transfertService.findPaginated(1, 5)).thenReturn(pageTransfert);
        when(transfertService.getTransaction(userTransaction1)).thenReturn(userTransaction1);


        mockMvc.perform(post("/user/transfert")
                        .param("userDestinationId", userDestinationId.toString())
                        .param("amount", transactionAmount.toString())
                        .param("comments", "velo")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/transfert?success"));
    }

    @WithUserDetails("aimenjerbi@gmail.com")
    @Test
    void PostTransfert_ErrorWithAmountNull() throws Exception {
        user1.getConnections().add(user3);
        BigDecimal transactionAmount = new BigDecimal("33");
        Long userDestinationId = 3L;

        when(userService.getCurrentUser()).thenReturn(user1);
        when(userService.findById(3L)).thenReturn(user3);
        when(transfertService.findPaginated(1, 5)).thenReturn(pageTransfert);
        when(transfertService.getTransaction(userTransaction1)).thenReturn(userTransaction1);


        mockMvc.perform(post("/user/transfert")
                        .param("userDestinationId", userDestinationId.toString())
                        .param("amount", transactionAmount.toString())
                        .param("comment", "")
                        .with(csrf()))
                        .andExpect((model().errorCount(1)))
                        .andExpect(status().isOk());
    }

    @WithUserDetails("aimenjerbi@gmail.com")
    @Test
    void PostTransfert_ErrorWithErrorConnection() throws Exception {
        user1.getConnections().add(user3);
        BigDecimal transactionAmount = new BigDecimal("33");
        Long userDestinationId = 0L;

        when(userService.getCurrentUser()).thenReturn(user1);
        when(userService.findById(0L)).thenReturn(user2);
        when(transfertService.findPaginated(1, 5)).thenReturn(pageTransfert);
        when(transfertService.getTransaction(userTransaction1)).thenReturn(userTransaction1);


        mockMvc.perform(post("/user/transfert")
                        .param("userDestinationId", userDestinationId.toString())
                        .param("amount", transactionAmount.toString())
                        .param("comment", "velo")
                        .with(csrf()))
                .andExpect((model().errorCount(2)))
                .andExpect(model().attributeHasFieldErrorCode("userTransactionDTO", "userDestinationId", "userDestinationNotABuddy"));
    }

    @WithUserDetails("aimenjerbi@gmail.com")
    @Test
    void PostTransfert_ErrorWithInsufficientBalance() throws Exception {
        user1.getConnections().add(user3);
        BigDecimal transactionAmount = new BigDecimal("200");

        Long userDestinationId = 3L;
        UserTransactionDTO  test = new UserTransactionDTO(userDestinationId,transactionAmount,"velo");
        when(userService.getCurrentUser()).thenReturn(user1);
        when(userService.findById(3L)).thenReturn(user3);
        when(transfertService.findPaginated(1, 5)).thenReturn(pageTransfert);
        when(transfertService.getTransaction(userTransaction1)).thenReturn(userTransaction1);


        mockMvc.perform(post("/user/transfert")
                        .param("userDestinationId", userDestinationId.toString())
                        .param("amount", transactionAmount.toString())
                        .param("comment", "velo")
                        .with(csrf()))
                .andExpect((model().errorCount(2)))
                .andExpect(model().attributeHasFieldErrorCode("userTransactionDTO", "amount", "insufficientBalance"));
    }

}
