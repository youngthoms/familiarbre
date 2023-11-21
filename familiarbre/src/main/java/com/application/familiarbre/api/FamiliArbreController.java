package com.application.familiarbre.api;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class FamiliArbreController {
    @GetMapping
    public ResponseEntity<String> index() {
        return ResponseEntity.ok("ok");
    }
}
