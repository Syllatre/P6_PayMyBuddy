package Service;

import com.application.paymybuddy.model.BankTransaction;
import com.application.paymybuddy.model.User;
import com.application.paymybuddy.repository.BankTransactionRepository;
import com.application.paymybuddy.repository.UserRepository;
import com.application.paymybuddy.service.BankTransactionService;
import com.application.paymybuddy.service.LocalDateTimeService;
import com.application.paymybuddy.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BankTransactionServiceTest {

    @InjectMocks
    private BankTransactionService bankTransactionService;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BankTransactionRepository bankTransactionRepository;

    @Mock
    private LocalDateTimeService localDateTimeService;

    User user;
    LocalDateTime now;
    BankTransaction bankTransaction;

    @BeforeEach
    void init() {
        user = new User(1L, "toto", "titi", "akira", "akira@gmail.com", "1234", new BigDecimal(10.0), new HashSet<>(),
                new HashSet<>(), new HashSet<>(), new HashSet<>(), true);
        now = LocalDateTime.of(2022, Month.AUGUST, 2, 10, 33, 48);
        bankTransaction = new BankTransaction(1L, user, "45458595", now, new BigDecimal(100.00));
    }


    @Test
    void getTransactionTest() {
        when(userService.getCurrentUser()).thenReturn(user);
        when(localDateTimeService.now()).thenReturn(now);
        when(bankTransactionRepository.save(bankTransaction)).thenReturn(bankTransaction);
        when(userRepository.save(user)).thenReturn(user);

        bankTransactionService.getTransaction(bankTransaction);

        verify(bankTransactionRepository, times(1)).save(bankTransaction);


        assertEquals(bankTransaction.getBankAccountNumber(), "45458595");
        assertEquals(bankTransaction.getUser().getUserId(), 1L);
        BigDecimal balance = new BigDecimal(110);
        assertEquals(user.getBalance(), balance);
    }

    @Test
    void findPaginatedTest() {
        when(userService.getCurrentUser()).thenReturn(user);

        List<BankTransaction> bankTransactions = new ArrayList<>();
        bankTransactions.add(bankTransaction);
        Page<BankTransaction> expectedPage = new PageImpl<>(bankTransactions);
        when(bankTransactionRepository.findByUser(any(User.class), any(Pageable.class))).thenReturn(expectedPage);

        Page<BankTransaction> pageBank = bankTransactionService.findPaginated(1, 5);
        assertEquals(pageBank.getTotalPages(), 1);
        assertTrue(pageBank.getContent().stream().anyMatch(o -> o.getBankAccountNumber().equals("45458595")));
        assertTrue(pageBank.getContent().stream().anyMatch(o -> o.getUser().getFirstName().equals("toto")));
    }
    @Test
    void localDateTimeServiceTest(){
        when(localDateTimeService.now()).thenReturn(now);
        LocalDateTime test = localDateTimeService.now();
        assertEquals(test,now);
    }
}
