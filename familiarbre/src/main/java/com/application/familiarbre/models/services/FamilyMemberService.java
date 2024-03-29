package com.application.familiarbre.models.services;

import com.application.familiarbre.api.UpdateRequest;
import com.application.familiarbre.models.dao.FamilyMemberRepository;
import com.application.familiarbre.models.entites.*;
import com.application.familiarbre.models.helper.FamilyMemberHelper;
import com.application.familiarbre.models.helper.FamilyTreeHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FamilyMemberService {
    private FamilyMemberRepository repository;

    public List<FamilyMember> getAll() {
        return repository.findAll();
    }

    public List<FamilyMember> getAllPublic() {
        return repository.findByStatus(Status.PUBLIC);
    }

    public FamilyMember getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Optional<FamilyMember> getByUser(User user) {
        return repository.findByUser(user);
    }

    public List<Node> hasAccess(Long idCurrentUser, Long idUserTarget) {
        FamilyMember target = getById(idUserTarget);
        Status targetStatus = target.getStatus();

        if (idCurrentUser == null && targetStatus == Status.PUBLIC) {
            return getFamilyTree(idCurrentUser, idUserTarget);
        }

        if (targetStatus == Status.PUBLIC || targetStatus == null) {
            return getFamilyTree(idCurrentUser, idUserTarget);
        } else if (targetStatus == Status.PROTECTED && (isInFamilyTree(getById(idCurrentUser), getById(idCurrentUser)) | isInFamilyTree(target, getById(idCurrentUser)))) {
            return getFamilyTree(idCurrentUser, idUserTarget);
        } else if ((targetStatus == Status.PRIVATE) && (Objects.equals(idUserTarget, idCurrentUser))) {
            return getFamilyTree(idCurrentUser, idUserTarget);
        }
        throw new org.springframework.security.access.AccessDeniedException("403 returned");
    }

    public List<Node> getFamilyTree(Long idCurrentUser, Long idUserTarget) {
        if (idCurrentUser == null) {
            FamilyMember test = null;
            return getFamilyTree(test, getById(idUserTarget));
        }
        return getFamilyTree(getById(idCurrentUser), getById(idUserTarget));
    }

    public List<Node> getFamilyTree(FamilyMember currentUser, FamilyMember familyMember) {
        return FamilyTreeHelper.getFamilyTree(currentUser, familyMember, this.getAll());
    }

    public void getParentsList(FamilyMember currentUser, FamilyMember familyMember, List<FamilyMember> familyTree) {
        FamilyTreeHelper.getParentsList(currentUser, familyMember, familyTree);
    }

    public void getChildList(FamilyMember currentUser, FamilyMember familyMember, List<FamilyMember> familyTree) {
        FamilyTreeHelper.getChildList(currentUser, familyMember, familyTree, this.getAll());
    }

    private void addChild(FamilyMember currentUser, List<FamilyMember> familyTree, FamilyMember familyMember1) {
        FamilyTreeHelper.addChild(currentUser, familyTree, familyMember1, this.getAll());
    }


    public boolean isInFamilyTree(FamilyMember user1, FamilyMember user2) {
        return FamilyTreeHelper.isInFamilyTree(user1, user2);
    }

    public FamilyMember getBySocialSecurityNumber(String socialSecurityNumber) {
        Optional<FamilyMember> fm = repository.findBySocialSecurityNumber(socialSecurityNumber);
        return fm.orElse(null);
    }

    public FamilyMember getByUserId(Long userId) {
        Optional<FamilyMember> fm = repository.findByUserId(userId);
        return fm.orElse(null);
    }

    public void setStatus(FamilyMember familyMember, Status privacy) {
        familyMember.setStatus(privacy);

        repository.save(familyMember);
    }

    @Transactional
    public void addSpouse(FamilyMember member1, FamilyMember member2) {
        List<FamilyMember> addList = member1.getPids();
        addList.add(member2);
        member1.setPids(addList);
        repository.save(member1);
        addList = member2.getPids();
        addList.add(member1);
        member2.setPids(addList);
        repository.save(member2);
    }

    public java.sql.Date parseDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return java.sql.Date.valueOf(localDate);
    }

    @Transactional
    public void update(FamilyMember member, UpdateRequest updateRequest) {
        if (member == null) {
            FamilyMember fm = new FamilyMember();
            if (updateRequest.getSocialSecurityNumber() != null) {
                fm.setSocialSecurityNumber(updateRequest.getSocialSecurityNumber());
            }
            if (updateRequest.getMid() != null && getById(updateRequest.getMid()) != null) {
                fm.setMid(getById(updateRequest.getMid()));
            } else if (updateRequest.getMid() != null && getById(updateRequest.getMid()) != null) {
                fm.setMid(getById(updateRequest.getMid()));
            }
            if (updateRequest.getFid() != null && getById(updateRequest.getFid()) != null) {
                fm.setFid(getById(updateRequest.getFid()));
            } else if (updateRequest.getMid() != null && getById(updateRequest.getMid()) != null) {
                fm.setFid(getById(updateRequest.getFid()));
            }

            fm.setGender(Gender.valueOf(updateRequest.getGender()));
            if (updateRequest.getName() != null) {
                fm.setFirstName(updateRequest.getName().split(" ")[0]);
                fm.setLastName(updateRequest.getName().split(" ")[1]);
            }

            List<FamilyMember> familyMemberList = new ArrayList<>();
            for (Long i : updateRequest.getPids()) {
                familyMemberList.add(getById(i));

            }

            fm.setPids(familyMemberList);
            repository.save(fm);
        } else {
            if (updateRequest.getBirthDay() != null) {
                member.setBirthDay(parseDate(updateRequest.getBirthDay()));
            }
            if (updateRequest.getFid() != null && (member.getFid() == null || member.getFid().getId() != updateRequest.getFid()) && getById(updateRequest.getFid()) != null) {
                member.setFid(getById(updateRequest.getFid()));
            } else if (updateRequest.getFid() != null && member.getFid() == null) {
                FamilyMember dad = new FamilyMember();
                dad.setGender(Gender.male);
                repository.save(dad);
                member.setFid(dad);
            }
            if (updateRequest.getMid() != null && (member.getMid() == null || member.getMid().getId() != updateRequest.getMid()) && getById(updateRequest.getMid()) != null) {
                member.setMid(getById(updateRequest.getMid()));
            } else if (updateRequest.getMid() != null && member.getMid() == null) {
                FamilyMember mom = new FamilyMember();
                mom.setGender(Gender.female);
                repository.save(mom);
                member.setMid(mom);
            }

            if (updateRequest.getMid() != null && getById(updateRequest.getMid()) != null && updateRequest.getFid() != null && getById(updateRequest.getFid()) == null) {
                if (!member.getMid().getPids().isEmpty()) {
                    List temp = getById(updateRequest.getMid()).getPids();
                    temp.remove(getById(updateRequest.getMid()).getPids().size() - 1);
                    getById(updateRequest.getMid()).setPids(temp);
                }
            }
            if (updateRequest.getFid() != null && getById(updateRequest.getFid()) != null && updateRequest.getMid() != null && getById(updateRequest.getMid()) == null) {
                if (!member.getFid().getPids().isEmpty()) {
                    List temp = getById(updateRequest.getFid()).getPids();
                    temp.remove(getById(updateRequest.getFid()).getPids().size() - 1);
                    getById(updateRequest.getFid()).setPids(temp);
                }
            }
            if (member.getMid() != null && member.getFid() != null) {
                FamilyMember mom = member.getMid();
                FamilyMember dad = member.getFid();

                mom.addPid(dad);
                dad.addPid(mom);
            }
            if (updateRequest.getName() != null && member.getFullName() != updateRequest.getName()) {
                member.setFirstName(updateRequest.getName().split(" ")[0]);
                member.setLastName(updateRequest.getName().split(" ")[1]);
            }

            for (Long i : updateRequest.getPids()) {
                if (i != null && getById(i) == null) {
                    if (member.getGender() == Gender.male) {
                        FamilyMember wife = new FamilyMember();
                        wife.setGender(Gender.female);
                        wife.addPid(member);
                        repository.save(wife);
                        member.addPid(wife);
                    } else {
                        FamilyMember husband = new FamilyMember();
                        husband.setGender(Gender.male);
                        husband.addPid(member);
                        repository.save(husband);
                        member.addPid(husband);
                    }
                }
            }

            repository.save(member);
        }
    }

    @Transactional
    public void addChild(FamilyMember parent, FamilyMember child) {
        if (parent == null || child == null) {
            throw new IllegalArgumentException("Parent and child must not be null");
        }

        Gender parentGender = parent.getGender();

        if (parentGender == null) {
            throw new IllegalStateException("Parent gender must not be null");
        }

        // Setting mother
        if (parentGender.equals(Gender.female)) {
            if (child.getMid() != null) {
                // Handle the case where a mother is already set
                throw new IllegalStateException("Child already has a mother set");
            }
            child.setMid(parent);
        }

        // Setting father
        if (parentGender.equals(Gender.male)) {
            if (child.getFid() != null) {
                // Handle the case where a father is already set
                throw new IllegalStateException("Child already has a father set");
            }
            child.setFid(parent);
        }

        repository.save(child);
    }

    @Transactional
    public void removeChild(FamilyMember child) {
        if (child == null) {
            throw new IllegalArgumentException("Child cannot be null");
        }

        child.setMid(null);
        child.setFid(null);

        repository.save(child);
    }

    @Transactional
    public FamilyMember save(FamilyMember fm) {
        return repository.save(fm);
    }

    @Transactional
    public FamilyMember create() {
        FamilyMember familyMember = new FamilyMember();

        return repository.save(familyMember);
    }

}
