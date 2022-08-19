//package com.application.paymybuddy.Controller;
//
//import com.application.paymybuddy.model.User;
//import com.application.paymybuddy.service.BankTransactionService;
//import com.application.paymybuddy.service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import javax.transaction.Transactional;
//import java.math.BigDecimal;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@Transactional
//public class BankTransactionControllerTestIT {
//
//
//
//        @Autowired
//        private MockMvc mvc;
//
//        @Autowired
//        private UserService userservice;
//
//        @Autowired
//        private BankTransactionService bankTransactionService;
//
//        @BeforeEach
//        void initializeDatabaseValues () {
//            User userTest = userservice.findByEmail("aimenjerbi@gmail.com");
//            userTest.setBalance(new BigDecimal("1000"));
//            userservice.save(userTest);
//        }
//
//        @Test
//        @WithMockUser(username="aimenjerbi@gmail.com")
//        void getBanktransactionShouldReturnOK() throws Exception {
//            mvc.perform(MockMvcRequestBuilders.get("/user/bank"))//.andDo(print())
//                    .andExpect(status().isOk());
//        }
//
//    }
