package com.application.paymybuddy.Controller.IT;

import com.application.paymybuddy.model.BankTransaction;
import com.application.paymybuddy.model.User;
import com.application.paymybuddy.service.BankTransactionService;
import com.application.paymybuddy.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BankTransactionControllerIT {


    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService userservice;

    @Autowired
    private BankTransactionService bankTransactionService;

    @BeforeEach
    void initializeDatabaseValues() {
        User userTest = userservice.findByEmail("aimenjerbi@gmail.com");
        userTest.setBalance(new BigDecimal("1000"));
        userservice.save(userTest);
    }

    @Test
    @WithMockUser(username = "aimenjerbi@gmail.com", password = "$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u", authorities = "USER")
    void getBanktransactionShouldReturnOK() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/user/bank")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "aimenjerbi@gmail.com", password = "$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u", authorities = "USER")
    void postBanktransactionGetMoney() throws Exception {
        mvc.perform(post("/user/bank")
                        .param("bankAccountNumber", "12345")
                        .param("amount", "100")
                        .with(csrf())
                ).andDo(print())
                .andExpect(status().is3xxRedirection());

        User userTest = userservice.findByEmail("aimenjerbi@gmail.com");
        assertEquals(new BigDecimal("1100"), userTest.getBalance(), "1000 + bank transaction");
        Set<BankTransaction> banktransactions = new HashSet<>( userTest.getBankTransactions());
        banktransactions.removeIf(e->e.getBankAccountNumber().equals("45124512"));
        assertEquals(1, banktransactions.size());

        BankTransaction bankTransaction = banktransactions.iterator().next();
        assertEquals(userTest, bankTransaction.getUser());

        assertEquals(new BigDecimal("100"), bankTransaction.getAmount());
    }
}
