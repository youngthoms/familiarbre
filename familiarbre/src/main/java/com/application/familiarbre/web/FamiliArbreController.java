package com.application.familiarbre.web;

import com.application.familiarbre.dao.UserRepository;
import com.application.familiarbre.entites.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class FamiliArbreController {
    @Autowired
    UserRepository userRepository;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/users")
    public String contacts(Model model) throws InterruptedException {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "liste";
    }
}
