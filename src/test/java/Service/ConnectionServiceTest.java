package Service;

import com.application.paymybuddy.model.BankTransaction;
import com.application.paymybuddy.model.User;
import com.application.paymybuddy.repository.UserRepository;
import com.application.paymybuddy.service.ConnectionService;
import com.application.paymybuddy.service.UserService;
import groovyjarjarantlr4.v4.runtime.misc.Array2DHashSet;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConnectionServiceTest {

    @InjectMocks
    private ConnectionService connectionService;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    User currentUser;
    Set<User> connection;
    User user1;
    User user2;

    @BeforeEach
    void init(){
        connection = new HashSet<>();

         user1 = new User(2L, "sif", "kessi", "hades", "hades@gmail.com", "1234", new BigDecimal(10.0), new HashSet<>(),
                new HashSet<>(), new HashSet<>(), new HashSet<>(), true);

         user2 = new User(3L, "nicolas", "lietard", "gimme", "gimme@gmail.com", "1234", new BigDecimal(10.0), new HashSet<>(),
                new HashSet<>(), new HashSet<>(), new HashSet<>(), true);

        connection.add(user1);
        connection.add(user2);

        currentUser = new User(1L, "toto", "titi", "akira", "akira@gmail.com", "1234", new BigDecimal(10.0), new HashSet<>(),
                new HashSet<>(), new HashSet<>(), new HashSet<>(), true);
    }

    @Test
    void pageConnectionTest(){
        when(userService.getCurrentUser()).thenReturn(currentUser);
        List<User> connection1 = new ArrayList<>();
        connection1.add(user1);
        connection1.add(user2);
        Page<User> expectedPage = new PageImpl<>(connection1);
        when(userRepository.findConnectionById(any(Long.class), any(Pageable.class))).thenReturn(expectedPage);

        Page<User> pageUser = connectionService.getAllConnectionByCurrentUser(1, 5);
        assertEquals(pageUser.getTotalPages(), 1);
        assertTrue(pageUser.getContent().stream().anyMatch(o -> o.getLastName().equals("kessi")));
        assertTrue(pageUser.getContent().stream().anyMatch(o -> o.getLastName().equals("lietard")));
    }
}
