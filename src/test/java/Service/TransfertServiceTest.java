package Service;

import com.application.paymybuddy.model.User;
import com.application.paymybuddy.model.UserTransaction;
import com.application.paymybuddy.repository.TransfertRepository;
import com.application.paymybuddy.repository.UserRepository;
import com.application.paymybuddy.service.LocalDateTimeService;
import com.application.paymybuddy.service.TransfertService;
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
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransfertServiceTest {

    @InjectMocks
    TransfertService transfertService;

    @Mock
    private TransfertRepository transfertRepository;
    @Mock
    private UserService userService;
    @Mock
    private LocalDateTimeService localDateTimeService;
    @Mock
    private UserRepository userRepository;

    User user;
    User user1;
    User user2;
    LocalDateTime now;
    UserTransaction userTransaction1;
    UserTransaction userTransaction2;

    @BeforeEach
     void init(){
         user = new User(1L, "toto", "titi", "akira", "akira@gmail.com", "1234", new BigDecimal(1000.0), new HashSet<>(),
                 new HashSet<>(), new HashSet<>(), new HashSet<>(), true);
         now = LocalDateTime.of(2022, Month.AUGUST, 2, 10, 33, 48);

         user1 = new User(2L, "sif", "kessi", "hades", "hades@gmail.com", "1234", new BigDecimal(10.0), new HashSet<>(),
                 new HashSet<>(), new HashSet<>(), new HashSet<>(), true);

         user2 = new User(3L, "nicolas", "lietard", "gimme", "gimme@gmail.com", "1234", new BigDecimal(10.0), new HashSet<>(),
                 new HashSet<>(), new HashSet<>(), new HashSet<>(), true);

         userTransaction1 = new UserTransaction(1L,user,user1,now,"manteau",new BigDecimal(100),new BigDecimal(0.5));
         userTransaction2 = new UserTransaction(2L,user1,user,now,"chapeau",new BigDecimal(200),new BigDecimal(1));
     }

    @Test
    void findPaginatedTest() {
        when(userService.getCurrentUser()).thenReturn(user);

        List<UserTransaction> userTransactionList = new ArrayList<>();
        userTransactionList.add(userTransaction1);
        userTransactionList.add(userTransaction2);
        Page<UserTransaction> expectedPage = new PageImpl<>(userTransactionList);
        when(transfertRepository.findByUserSourceOrUserDestination(any(User.class),any(User.class), any(Pageable.class))).thenReturn(expectedPage);

        Page<UserTransaction> pageTransaction = transfertService.findPaginated(1, 5);

        assertEquals(pageTransaction.getTotalPages(), 1);
        assertTrue(pageTransaction.getContent().stream().anyMatch(o -> o.getComments().equals("manteau")));
        assertTrue(pageTransaction.getContent().stream().anyMatch(o -> o.getComments().equals("chapeau")));
        assertEquals(pageTransaction.getContent().size(),2);
    }

    @Test
    void getTransactionTest(){
        when(userService.getCurrentUser()).thenReturn(user);
        when(transfertRepository.save(userTransaction1)).thenReturn(userTransaction1);
        when(localDateTimeService.now()).thenReturn(now);
        when(userRepository.findByEmail("akira@gmail.com")).thenReturn(user);
        when(userRepository.findByEmail("hades@gmail.com")).thenReturn(user1);
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.save(user1)).thenReturn(user1);

         UserTransaction  transactionTest = transfertService.getTransaction(userTransaction1);

        assertEquals(transactionTest.getUserSource().getUserName(),"akira");
        assertEquals(transactionTest.getUserDestination().getUserName(),"hades");
        assertEquals(transactionTest.getUserDestination().getBalance(), new BigDecimal(110));
        assertEquals(transactionTest.getUserSource().getBalance(),new BigDecimal(899.5));
    }

    @Test
    void getFeesTest(){
        BigDecimal feesTest1 = transfertService.getFees(new BigDecimal(100));
        BigDecimal feesTest2 = transfertService.getFees(new BigDecimal(150));

        assertEquals(feesTest1,new BigDecimal(0.5));
        assertEquals(feesTest2,new BigDecimal(0.75));
    }
}
