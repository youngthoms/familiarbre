package com.application.familiarbre.api;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class FamiliArbreController {
    @GetMapping("")
    public IndexResponse index() {
        return new IndexResponse("Suuuup");
    }
}

class IndexResponse {
    private String message;

    public IndexResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
