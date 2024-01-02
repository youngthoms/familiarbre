package com.application.familiarbre.authentication;

import lombok.*;

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String token;
    private Long userId;
    private String refreshToken;
}
