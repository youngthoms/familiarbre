package com.application.familiarbre.api;

import com.application.familiarbre.authentication.AuthenticationRequest;
import com.application.familiarbre.authentication.AuthenticationResponse;
import com.application.familiarbre.authentication.AuthenticationService;
import com.application.familiarbre.authentication.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthenticationController {

    private final AuthenticationService service;

    @GetMapping
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("ping pong");
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
