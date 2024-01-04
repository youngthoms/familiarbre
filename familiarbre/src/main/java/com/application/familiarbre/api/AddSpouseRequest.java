package com.application.familiarbre.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class AddSpouseRequest {
    private Long member1;
    private Long member2;
}
