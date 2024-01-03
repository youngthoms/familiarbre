package com.application.familiarbre.authentication;

import com.application.familiarbre.models.entites.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    // User
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    // FamilyMember
    private String birthDay;
    private Gender gender;
    private String socialSecurityNumber;

}
