package com.application.paymybuddy.Controller;

import com.application.paymybuddy.ConfigTest.ConfigurationTest;
import com.application.paymybuddy.controller.RegisterController;
import com.application.paymybuddy.service.UserService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegisterController.class)
@Import(ConfigurationTest.class)
public class RegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @MockBean
    private ModelMapper modelMapperMock;

    @Test
    void register_shouldSucceed() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("register"))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("userFormDTO"));
    }

    @Test
    void PostRegistrationForm_shouldSucceedAndRedirected() throws Exception {
        mockMvc.perform(post("/register")
                        .param("firstName", "aimen")
                        .param("lastName", "jerbi")
                        .param("userName", "akira")
                        .param("email", "aimenjerbi@gmail.com")
                        .param("password", "1234")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/register?success"));
    }

    @Test
    void registerWithUserAlreadyExist() throws Exception {
        //ARRANGE
        when(userService.existsByEmail("aimenjerbi@gmail.com")).thenReturn(Boolean.TRUE);

        mockMvc.perform(post("/register")
                        .param("firstName", "aimen")
                        .param("lastName", "jerbi")
                        .param("userName", "akira")
                        .param("email", "aimenjerbi@gmail.com")
                        .param("password", "1234")
                        .with(csrf()))
                .andExpect((model().errorCount(1)))
                .andExpect(status().isOk());
    }

    @Test
    void registerWithUserNameAlreadyExist() throws Exception {
        //ARRANGE
        when(userService.existsByUserName("akira")).thenReturn(Boolean.TRUE);

        mockMvc.perform(post("/register")
                        .param("firstName", "aimen")
                        .param("lastName", "jerbi")
                        .param("userName", "akira")
                        .param("email", "aimenjerbi@gmail.com")
                        .param("password", "1234")
                        .with(csrf()))
                .andExpect((model().errorCount(1)))
                .andExpect(status().isOk());
    }
}
