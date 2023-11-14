package com.application.familiarbre.models.dao;

import com.application.familiarbre.models.entites.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyMemberRepository  extends JpaRepository<FamilyMember, Long> {
}
