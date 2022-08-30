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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
//We need to use @Transactional here because otherwise we get LazyInitializationException due to User.roles/banktransactions/connections are FetchType.LAZY
//Session does not exist in test context, @Transactional creates a session context, this way we can fetch lazily roles/banktransactions/connections
//https://stackoverflow.com/questions/19813492/getting-lazyinitializationexception-on-junit-test-case#answer-20985746
//Another solution would be to use FetchType.EAGER, but THIS HAS A NEGATIVE IMPACT:
//it can lead to performance issues in the real application, explanations here: https://vladmihalcea.com/eager-fetching-is-a-code-smell
//NOTE: @Transactional creates a new transaction that is by default automatically ROLLED BACK after test completion.
@Transactional
class TransfertControllerIT {


    @Autowired
    private MockMvc mvc;

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
    @WithMockUser(username = "aimenjerbi@gmail.com",password = "$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u")
    void getUsertransactionShouldReturnOK() throws Exception {
        mvc.perform(get("/user/transfert")).andDo(print()).andExpect(status().isOk());
    }
}

