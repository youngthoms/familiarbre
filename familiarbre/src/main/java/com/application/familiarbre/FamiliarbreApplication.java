package com.application.familiarbre;

import com.application.familiarbre.authentication.AuthenticationService;
import com.application.familiarbre.authentication.RegisterRequest;
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
        if (userService.emailExists("user@mail.com")) {
            return null;
        }
        return args -> {
            var user = RegisterRequest.builder()
                    .email("user@mail.com")
                    .password("password")
                    .build();
            System.out.println("User token: " + service.register(user).getToken());
        };
    }
}
