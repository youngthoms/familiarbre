package com.application.familiarbre.dao;

import com.application.familiarbre.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
