package com.application.familiarbre.api;

import com.application.familiarbre.models.entites.User;
import com.application.familiarbre.models.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> contacts(Model model) {
        List<User> users = userService.getAll();
        model.addAttribute("users", users);

        return users;
    }

    @GetMapping("/id/{id}")
    public User byId(@PathVariable Long id) {
        return userService.getById(id);
    }
}
