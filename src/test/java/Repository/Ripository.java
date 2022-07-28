package Repository;

import com.application.paymybuddy.model.User;
import com.application.paymybuddy.model.UserTransaction;
import com.application.paymybuddy.repository.TransfertRepository;
import com.application.paymybuddy.repository.UserRepository;
import com.application.paymybuddy.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public class Ripository {

    UserRepository userRepository;

    TransfertRepository transfertRepository;

    public Ripository(ConnectionService connectionService, UserRepository userRepository, TransfertRepository transfertRepository) {
        this.userRepository = userRepository;
        this.transfertRepository = transfertRepository;
    }

    @Test
    public void Tew(){

//        Page<User> pageConnection = connectionService.getAllConnectionByCurrentUser(1, 5);
//        List<User> connection = pageConnection.getContent();

//        Pageable pageable = PageRequest.of(1, 5);
//      Page <User> connection = userRepository.findConnectionById(1L,pageable);
//                connection.forEach(System.out::println);
//        List<User> users = userRepository.findAll();
        User user = userRepository.findByEmail("aimenjerbi@gmail.com");
        System.out.println(user);
//              Page <UserTransaction> connection = transfertRepository.findByUserSourceOrUserDestination(user, user,pageable);
//                connection.forEach(System.out::println);
    }
}
