package com.application.familiarbre.models.dao;

import com.application.familiarbre.models.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
