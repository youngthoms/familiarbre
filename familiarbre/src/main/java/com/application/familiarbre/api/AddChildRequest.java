package com.application.familiarbre.api;

import com.application.familiarbre.models.entites.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class AddChildRequest {
    private Long dadId;
    private Long momId;
    private Long childId;
    private Gender gender;
}
