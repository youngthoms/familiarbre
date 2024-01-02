package com.application.familiarbre.models.dao;

import com.application.familiarbre.models.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u JOIN u.tokens t WHERE t.token = :token")
    Optional<User> findByToken(@Param("token") String token);
}
