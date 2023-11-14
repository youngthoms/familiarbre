package com.application.familiarbre.api;

import com.application.familiarbre.models.dao.UserRepository;
import com.application.familiarbre.models.entites.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FamiliArbreController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("")
    public User index() {
        return new User();
    }

    @GetMapping("/users")
    public List<User> contacts(Model model) throws InterruptedException {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);

        return users;
    }
}

class IndexReponse {
    private String message;

    public IndexReponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
