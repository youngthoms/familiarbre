package com.application.familiarbre.api;


import com.application.familiarbre.models.dao.UserRepository;
import com.application.familiarbre.models.entites.User;
import com.application.familiarbre.models.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> contacts(Model model) throws InterruptedException {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);

        return users;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

}
