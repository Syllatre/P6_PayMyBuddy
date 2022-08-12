//package Controller;
//
//import ConfigTest.ConfigurationTest;
//import com.application.paymybuddy.controller.BankTransactionController;
//import com.application.paymybuddy.model.BankTransaction;
//import com.application.paymybuddy.model.User;
//import com.application.paymybuddy.model.UserTransaction;
//import com.application.paymybuddy.service.BankTransactionService;
//import com.application.paymybuddy.service.UserService;
//import lombok.AllArgsConstructor;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.security.test.context.support.WithUserDetails;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.Currency;
//import java.util.HashSet;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
//
//@WebMvcTest(controllers = BankTransactionController.class)
//@Import( ConfigurationTest.class)
//@AllArgsConstructor
//@SpringBootTest(classes = BankTransactionController.class)
//public class BankTransactionControllerTest {
//
//    private MockMvc mockMvc;
//
//    @MockBean
//    private BankTransactionService bankTransactionService;
//
//    @MockBean
//    private UserService userService;
//
//    User user1;
//    BankTransaction bankTransaction1;
//    BankTransaction bankTransaction2;
//    BankTransaction bankTransaction3;
//    Page<BankTransaction> pageTransfert;
//
//    @BeforeEach
//    void setup() {
//        user1 = new User(1L, "firstname1", "lastname1", "userName1", "user1@mail.com", "password1", new BigDecimal(100),
//                new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), true);
//        bankTransaction1 = new BankTransaction(1L, user1, "12345", LocalDateTime.of(2025, 01, 01, 00, 45), new BigDecimal("100.10"));
//        bankTransaction2 = new BankTransaction(2L, user1, "12345", LocalDateTime.of(2025, 01, 01, 00, 45), new BigDecimal("200.20"));
//        bankTransaction3 = new BankTransaction(3L, user1, "12345", LocalDateTime.of(2025, 01, 01, 00, 45), new BigDecimal("300.30"));
//
//        BankTransaction[] bankTransactionArray = {bankTransaction1, bankTransaction2, bankTransaction3};
//        List<BankTransaction> bankTransactions = Arrays.asList(bankTransactionArray);
//        Page<BankTransaction> pageTransfert = new PageImpl<BankTransaction>(bankTransactions);
//    }
//
//    @WithUserDetails("user@company.com")
//    @Test
//    void GetBankTransaction_shouldSucceed() throws Exception {
//        //ARRANGE
//        when(userService.getCurrentUser()).thenReturn(user1);
//        when(bankTransactionService.findPaginated(1,5)).thenReturn(pageTransfert);
//
//        //ACT+ASSERT
//        mockMvc.perform(get("/user/bank"))
//                .andExpect(status().is2xxSuccessful())
//                .andExpect(view().name("/user/bank"))
//                .andExpect(model().size(3))
//                .andExpect(model().attributeExists("currentUser"))
//                .andExpect(model().attributeExists("bankTransactionDTO"))
//                .andExpect(model().attributeExists("transfert"))
//                .andExpect(model().attributeExists("pages"))
//                .andExpect(model().attributeExists("currentPage"));
//    }
//}
