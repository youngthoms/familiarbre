package com.application.familiarbre.models.dao;

import com.application.familiarbre.models.entites.FamilyMember;
import com.application.familiarbre.models.entites.Status;
import com.application.familiarbre.models.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FamilyMemberRepository  extends JpaRepository<FamilyMember, Long> {
    Optional<FamilyMember> findByUser(User user);
    Optional<FamilyMember> findByUserId(Long userId);
    Optional<FamilyMember> findBySocialSecurityNumber(String number);
    List<FamilyMember> findByStatus(Status status);

}
