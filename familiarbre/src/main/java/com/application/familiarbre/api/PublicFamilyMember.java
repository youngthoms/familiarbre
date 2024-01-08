package com.application.familiarbre.api;

import com.application.familiarbre.models.entites.Gender;
import com.application.familiarbre.models.entites.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class PublicFamilyMember {
    private Long id;
    private String fullName;
    private Gender gender;
    private Status status;
}
