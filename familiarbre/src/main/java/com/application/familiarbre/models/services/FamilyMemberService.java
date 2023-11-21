package com.application.familiarbre.models.services;

import com.application.familiarbre.models.dao.FamilyMemberRepository;
import com.application.familiarbre.models.entites.FamilyMember;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FamilyMemberService {
    private FamilyMemberRepository repository;

    public FamilyMember saveFamilyMember(FamilyMember familyMember) {
        return repository.save(familyMember);
    }

    public List<FamilyMember> getAll() {
        return repository.findAll();
    }

    public FamilyMember getById(Long id) {
        return repository.findById(id).orElseThrow();
    }
}
