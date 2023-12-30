package com.application.familiarbre.models.services;

import com.application.familiarbre.models.dao.FamilyMemberRepository;
import com.application.familiarbre.models.entites.FamilyMember;
import com.application.familiarbre.models.entites.Status;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public void getParentsList (FamilyMember familyMember, List<FamilyMember> familyTree){
         if(familyMember != null){
             familyTree.add(familyMember);
             if (familyMember.getMom()!=null){
                 getParentsList(familyMember.getMom(),familyTree);
             }
             if (familyMember.getDad()!=null){
                 getParentsList(familyMember.getDad(),familyTree);
             }
         }
    }

    public void getChildList(FamilyMember currentUser,FamilyMember familyMember, List<FamilyMember> familiyTree){
        for (FamilyMember familyMember1 : getAll()){
            if(familyMember==familyMember1.getDad()){
                if(familyMember1.getDad().getStatus()==Status.PRIVATE && familyMember1.getDad()==currentUser){
                    familiyTree.add(familyMember1);
                    getChildList(currentUser,familyMember1.getDad(),familiyTree);
                } else if (familyMember1.getDad().getStatus()==Status.PUBLIC) {
                    familiyTree.add(familyMember1);
                    getChildList(currentUser,familyMember1.getDad(),familiyTree);
                } else if (familyMember1.getDad().getStatus()==Status.PROTECTED && isInFamilyTree(currentUser,familyMember1.getDad()) ) {
                    familiyTree.add(familyMember1);
                    getChildList(currentUser,familyMember1.getDad(),familiyTree);
                }
            }
            if(familyMember == familyMember1.getMom()){
                if(familyMember1.getMom().getStatus() == Status.PRIVATE && familyMember1.getMom() == currentUser){
                    familiyTree.add(familyMember1);
                    getChildList(currentUser, familyMember1.getMom(), familiyTree);
                } else if (familyMember1.getMom().getStatus() == Status.PUBLIC) {
                    familiyTree.add(familyMember1);
                    getChildList(currentUser, familyMember1.getMom(), familiyTree);
                } else if (familyMember1.getMom().getStatus() == Status.PROTECTED && isInFamilyTree(currentUser, familyMember1.getMom())) {
                    familiyTree.add(familyMember1);
                    getChildList(currentUser, familyMember1.getMom(), familiyTree);
                }
            }

        }
    }

    public boolean rightToSeeParents(FamilyMember currentUser,FamilyMember reachUser){
        if (reachUser.getStatus()== Status.PRIVATE){
            if(currentUser==reachUser){
                return true;
            }
            return false;
        }
        else if (reachUser.getStatus()==Status.PUBLIC){
            return true;
        }
        else if(reachUser.getStatus()==Status.PROTECTED){
            return isInFamilyTree(currentUser, reachUser);
        }
        else{
            return false;
        }

    }

    public boolean isInFamilyTree(FamilyMember user1, FamilyMember user2){
        if(user1==user2){
            return true;
        }
        else{
            boolean momResult = false;
            boolean dadResult = false;

            if (user2.getMom()!=null){
                momResult=isInFamilyTree(user1,user2.getMom());
            }
            if (user2.getDad()!=null) {
                dadResult=isInFamilyTree(user1, user2.getDad());
            }
            return momResult || dadResult;
        }
    }
}
