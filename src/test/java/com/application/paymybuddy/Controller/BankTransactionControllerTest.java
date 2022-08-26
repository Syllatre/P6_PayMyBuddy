package com.application.paymybuddy.Controller;

import com.application.paymybuddy.ConfigTest.ConfigurationTest;
import com.application.paymybuddy.controller.BankTransactionController;
import com.application.paymybuddy.model.BankTransaction;
import com.application.paymybuddy.model.User;
import com.application.paymybuddy.service.BankTransactionService;
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
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BankTransactionController.class)
@Import(ConfigurationTest.class)
public class BankTransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankTransactionService bankTransactionService;

    @MockBean
    private UserService userService;

    User user1;
    BankTransaction bankTransaction1;
    BankTransaction bankTransaction2;
    BankTransaction bankTransaction3;
    Page<BankTransaction> pageTransfert;

    @BeforeEach
    void setup() {
        user1 = new User(1L, "firstname1", "lastname1", "userName1", "aimenjerbi@gmail.com", "password1", new BigDecimal(100),
                new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), true);
        bankTransaction1 = new BankTransaction(1L, user1, "12345", LocalDateTime.of(2022, 01, 01, 00, 45), new BigDecimal("100.10"));
        bankTransaction2 = new BankTransaction(2L, user1, "12345", LocalDateTime.of(2022, 01, 01, 00, 45), new BigDecimal("200.20"));
        bankTransaction3 = new BankTransaction(3L, user1, "12345", LocalDateTime.of(2022, 01, 01, 00, 45), new BigDecimal("300.30"));

        BankTransaction[] bankTransactionArray = {bankTransaction1, bankTransaction2, bankTransaction3};
        List<BankTransaction> bankTransactions = Arrays.asList(bankTransactionArray);
        Pageable pageable = PageRequest.of(1, 5);

        pageTransfert = new PageImpl<>(bankTransactions, pageable, 3);

    }

    @WithUserDetails("aimenjerbi@gmail.com")
    @Test
    void GetBankTransaction_shouldSucceed() throws Exception {
        //ARRANGE
        when(userService.getCurrentUser()).thenReturn(user1);
        when(bankTransactionService.findPaginated(1, 5)).thenReturn(pageTransfert);

        //ACT+ASSERT
        mockMvc.perform(get("/user/bank"))
                .andExpect(status().isOk())
                .andExpect(view().name("bank"))
                .andExpect(model().size(5))
                .andExpect(model().attributeExists("currentUser"))
                .andExpect(model().attributeExists("bankTransactionDTO"))
                .andExpect(model().attributeExists("transfert"))
                .andExpect(model().attributeExists("pages"))
                .andExpect(model().attributeExists("currentPage"));
    }

    @WithUserDetails("aimenjerbi@gmail.com")
    @Test
    void PostBankTransaction_SendMoneyShouldSucceedAndRedirected() throws Exception {
        BigDecimal transactionAmount = new BigDecimal("33");


        when(userService.getCurrentUser()).thenReturn(user1);
        when(bankTransactionService.findPaginated(1, 5)).thenReturn(pageTransfert);
        when((bankTransactionService.getTransaction(bankTransaction1))).thenReturn(bankTransaction1);

        mockMvc.perform(post("/user/bank")
                        .param("amount", transactionAmount.toString())
                        .param("bankAccountNumber", "1234")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/bank?success"));
    }

    @WithUserDetails("aimenjerbi@gmail.com")
    @Test
    void PostBankTransaction_ErrorArgument() throws Exception {
        BigDecimal transactionAmount = new BigDecimal("33");

        when(userService.getCurrentUser()).thenReturn(user1);
        when(bankTransactionService.findPaginated(1, 5)).thenReturn(pageTransfert);
        when((bankTransactionService.getTransaction(bankTransaction1))).thenReturn(bankTransaction1);


        mockMvc.perform(post("/user/bank")
                        .param("amount", transactionAmount.toString())
                        .param("bankAccountNumber", "")
                        .with(csrf()))
                        .andExpect((model().errorCount(1)))
                        .andExpect(status().isOk());
    }
}
