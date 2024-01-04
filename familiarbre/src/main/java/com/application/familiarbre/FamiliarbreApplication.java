package com.application.familiarbre;

import com.application.familiarbre.authentication.AuthenticationResponse;
import com.application.familiarbre.authentication.AuthenticationService;
import com.application.familiarbre.authentication.RegisterRequest;
import com.application.familiarbre.models.entites.FamilyMember;
import com.application.familiarbre.models.entites.Gender;
import com.application.familiarbre.models.entites.Status;
import com.application.familiarbre.models.services.FamilyMemberService;
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
            UserService userService,
            FamilyMemberService familyMemberService
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
            AuthenticationResponse response = service.register(user);
            System.out.println("User token: " + response.getToken());
            FamilyMember familyMemberUser = familyMemberService.getByUserId(response.getUserId());

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
                AuthenticationResponse responseUser2 = service.register(user2);
                System.out.println("User2 token: " + responseUser2.getToken());
                FamilyMember familyMemberUser2 = familyMemberService.getByUserId(responseUser2.getUserId());
                familyMemberUser2.setStatus(Status.PUBLIC);
                familyMemberService.addChild(familyMemberUser, familyMemberUser2);
            }
        }

        return null;
    }
}
