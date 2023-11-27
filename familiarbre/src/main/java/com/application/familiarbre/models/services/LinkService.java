package com.application.familiarbre.models.services;

import com.application.familiarbre.models.dao.LinkRepository;
import com.application.familiarbre.models.entites.FamilyMember;
import com.application.familiarbre.models.entites.Link;
import com.application.familiarbre.models.entites.User;

import java.util.Optional;

public class LinkService {
    private LinkRepository linkRepository;
    public Optional<Link> getParents (FamilyMember familyMember){
        if (linkRepository.findByChild(familyMember).isPresent()) {
            return linkRepository.findByChild(familyMember);
        }
        return null;
    }
}