package com.application.paymybuddy.Controller.IT;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ConnectionControllerIT {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(username = "aimenjerbi@gmail.com", password = "$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u", authorities = "USER")
    void GETconnectionPageShouldReturnOK() throws Exception {
        mvc.perform(get("/user/connection")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "aimenjerbi@gmail.com", password = "$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u", authorities = "USER")
    void POSTconnectionPageShouldReturnOK() throws Exception {
        mvc.perform(post("/user/connection")
                .param("email", "gimme@gmail.com")
                .with(csrf())).andExpect(status().is3xxRedirection());
    }



}
