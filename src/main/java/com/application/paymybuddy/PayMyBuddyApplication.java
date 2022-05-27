package com.application.paymybuddy;

import com.application.paymybuddy.model.User;
import com.application.paymybuddy.model.UserTransaction;
import com.application.paymybuddy.repository.UserTransactionRepository;
import com.application.paymybuddy.service.LocalDateTimeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.yaml.snakeyaml.introspector.BeanAccess;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@SpringBootApplication
public class PayMyBuddyApplication {

	public static void main(String[] args) {
		SpringApplication.run(PayMyBuddyApplication.class, args);
	}

//	@Bean
//	CommandLineRunner commandLineRunner(UserTransactionRepository userTransactionRepository,LocalDateTimeService localDateTimeService){
//		return args -> {
//			User userSource = new User();
//			User userDestination = new User();
//			User userDestinationMe = new User();
//			userDestinationMe.setUserId(2L);
//			userSource.setUserId(2L);
//			User userSourceDoris = new User();
//			userSourceDoris.setUserId(6L);
//			userDestination.setUserId(3L);
//			BigDecimal bigDecimal = new BigDecimal(450);
//			BigDecimal bigDecimal1 = new BigDecimal(450);
//			BigDecimal bigDecimal2 = new BigDecimal(450);
//			BigDecimal bigDecimal3 = new BigDecimal(450);
//			BigDecimal bigDecimal4 = new BigDecimal(450);
//			BigDecimal bigDecimal5 = new BigDecimal(450);
//			BigDecimal bigDecimal6 = new BigDecimal(450);
//			BigDecimal bigDecimal7 = new BigDecimal(450);
//			BigDecimal bigDecimal8 = new BigDecimal(450);
//			BigDecimal bigDecimal9 = new BigDecimal(80);
//			BigDecimal bigDecimal10 = new BigDecimal(35);
//			BigDecimal bigDecimal11 = new BigDecimal(230);
//			BigDecimal fees = new BigDecimal(2);
//			userTransactionRepository.save(new UserTransaction(userSource,userDestination,localDateTimeService.now(),"manteau noir",bigDecimal,fees));
//			userTransactionRepository.save(new UserTransaction(userSource,userDestination,localDateTimeService.now(),"manteau orange",bigDecimal1,fees));
//			userTransactionRepository.save(new UserTransaction(userSource,userDestination,localDateTimeService.now(),"manteau jaune",bigDecimal2,fees));
//			userTransactionRepository.save(new UserTransaction(userSource,userDestination,localDateTimeService.now(),"manteau violet",bigDecimal3,fees));
//			userTransactionRepository.save(new UserTransaction(userSource,userDestination,localDateTimeService.now(),"manteau noir",bigDecimal4,fees));
//			userTransactionRepository.save(new UserTransaction(userSource,userDestination,localDateTimeService.now(),"manteau vert",bigDecimal5,fees));
//			userTransactionRepository.save(new UserTransaction(userSource,userDestination,localDateTimeService.now(),"manteau rouge",bigDecimal6,fees));
//			userTransactionRepository.save(new UserTransaction(userSource,userDestination,localDateTimeService.now(),"manteau bleu",bigDecimal7,fees));
//			userTransactionRepository.save(new UserTransaction(userSource,userDestination,localDateTimeService.now(),"manteau cerise",bigDecimal8,fees));
//			userTransactionRepository.save(new UserTransaction(userSourceDoris,userDestination,localDateTimeService.now(),"table",bigDecimal9,fees));
//			userTransactionRepository.save(new UserTransaction(userSourceDoris,userDestinationMe,localDateTimeService.now(),"sac",bigDecimal10,fees));
//			userTransactionRepository.save(new UserTransaction(userSourceDoris,userDestination,localDateTimeService.now(),"velo",bigDecimal11,fees));
//		};
//	}
}
