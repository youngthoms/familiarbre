package com.application.familiarbre.models.services;

import com.application.familiarbre.models.dao.UserRepository;
import com.application.familiarbre.models.entites.Link;
import com.application.familiarbre.models.entites.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private final static String USER_NOT_FOUND_MESSAGE = "User with email %s not found";

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND_MESSAGE, email)));
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }
    public User loadUserByToken(String token) {
        Optional<User> user = userRepository.findByToken(token);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException("User not found for the token");
        }
    }
}
