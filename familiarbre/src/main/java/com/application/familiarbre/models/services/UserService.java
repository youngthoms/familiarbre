package com.application.familiarbre.models.services;

import com.application.familiarbre.models.dao.UserRepository;
import com.application.familiarbre.models.entites.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }


}
