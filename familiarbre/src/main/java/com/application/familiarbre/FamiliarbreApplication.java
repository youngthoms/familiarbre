package com.application.familiarbre;

import com.application.familiarbre.authentication.AuthenticationService;
import com.application.familiarbre.authentication.RegisterRequest;
import com.application.familiarbre.models.entites.Gender;
import com.application.familiarbre.models.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FamiliarbreApplication {

    public static void main(String[] args) {
        SpringApplication.run(FamiliarbreApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            AuthenticationService service,
            UserService userService
    ) {
        if (!userService.emailExists("user@mail.com")) {
            var user = RegisterRequest.builder()
                    .email("user@mail.com")
                    .password("password")
                    .gender(Gender.male)
                    .firstName("Sam")
                    .lastName("Femal")
                    .socialSecurityNumber("0102030405")
                    .birthDay("01/01/2000")
                    .build();
            System.out.println("User token: " + service.register(user).getToken());
        }

        if (!userService.emailExists("user2@mail.com")) {
            var user2 = RegisterRequest.builder()
                    .email("user2@mail.com")
                    .password("password")
                    .gender(Gender.female)
                    .firstName("Sam")
                    .lastName("Lebrise")
                    .socialSecurityNumber("0201030405")
                    .birthDay("02/01/2000")
                    .build();
            System.out.println("User2 token: " + service.register(user2).getToken());
        }

        return null;
    }
}
