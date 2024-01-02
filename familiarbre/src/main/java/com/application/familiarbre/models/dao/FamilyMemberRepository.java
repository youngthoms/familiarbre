package com.application.familiarbre.models.dao;

import com.application.familiarbre.models.entites.FamilyMember;
import com.application.familiarbre.models.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FamilyMemberRepository  extends JpaRepository<FamilyMember, Long> {
    Optional<FamilyMember> findByUser(User user);
}
