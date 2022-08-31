package com.application.paymybuddy.Controller.IT;


import com.application.paymybuddy.model.User;
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

import javax.transaction.Transactional;
import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TransfertControllerIT {

    @Autowired
    MockMvc mvc;

    @Autowired
    private UserService userservice;

    @BeforeEach
    void initializeDatabaseValues() {
        User userTest = userservice.findByEmail("aimenjerbi@gmail.com");
        userTest.setBalance(new BigDecimal("1000"));
        userservice.save(userTest);

        User userAnotherTest = userservice.findByEmail("gimme@gmail.com");
        userAnotherTest.setBalance(new BigDecimal("1000"));
        userservice.save(userAnotherTest);
    }

    @Test
    @WithMockUser(username = "aimenjerbi@gmail.com", password = "$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u", authorities = "USER")
    void getUsertransactionShouldReturnOK() throws Exception {
        mvc.perform(get("/user/transfert")).andDo(print()).andExpect(status().isOk());
    }

//    @Test
//    @WithMockUser(username = "aimenjerbi@gmail.com", password = "$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u", authorities = "USER")
//    void postUsertransaction() throws Exception {
//        mvc.perform(post("/user/transaction")
//                        .param("userDestinationId", "46")
//                        .param("amount", "100")
//                        .param("comments", "velo")
//                        .with(csrf())
//                ).andDo(print())
//                .andExpect(status().is3xxRedirection());

//        User userTest = userservice.findByEmail("aimenjerbi@gmail.com");
//        User userAnotherTest = userservice.findByEmail("gimme@gmail.com");
//        assertEquals(new BigDecimal("900.00"), userTest.getBalance(), "1000 - user transaction");
//        assertEquals(new BigDecimal("1099.50"), userAnotherTest.getBalance(), "1000 + user transaction - fees (0.5%)");
//
//        UserTransaction userTransaction = userTest.getUserTransactions().iterator().next();
//        assertEquals(userTest, userTransaction.getUserSource());
//        assertEquals(userAnotherTest, userTransaction.getUserDestination());
//        assertEquals(new BigDecimal("99.50"), userTransaction.getAmount());
//
//        assertEquals(new BigDecimal("0.50"), userTransaction.getFees());

//}

}
