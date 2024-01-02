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
    public List<FamilyMember> getFamilyTree(long idCurrentUser,long idUserTarget){
        return getFamilyTree(getById(idCurrentUser),getById(idUserTarget));
    }
    public List<FamilyMember> getFamilyTree(FamilyMember currentUser,FamilyMember familyMember){
        List<FamilyMember> familyTree=new ArrayList<FamilyMember>();
        getParentsList(currentUser,familyMember,familyTree);
        getChildList(currentUser,familyMember,familyTree);
        return familyTree;
    }

    public void getParentsList (FamilyMember currentUser, FamilyMember familyMember, List<FamilyMember> familyTree){
         if(familyMember != null){
             familyTree.add(familyMember);
             if (familyMember.getMom()!=null){
                 if(familyMember.getMom().getStatus() == Status.PRIVATE && familyMember.getMom() == currentUser){
                     getParentsList(currentUser, familyMember.getMom(), familyTree);
                 } else if (familyMember.getMom().getStatus() == Status.PUBLIC) {
                     getParentsList(currentUser, familyMember.getMom(), familyTree);
                 } else if (familyMember.getMom().getStatus() == Status.PROTECTED && isInFamilyTree(currentUser, familyMember.getMom())) {
                     getParentsList(currentUser, familyMember.getMom(), familyTree);
                 }
             }
             if (familyMember.getDad()!=null){
                 if (familyMember.getDad().getStatus() == Status.PRIVATE && familyMember.getDad() == currentUser) {
                     getParentsList(currentUser, familyMember.getDad(), familyTree);
                 } else if (familyMember.getDad().getStatus() == Status.PUBLIC) {
                     getParentsList(currentUser, familyMember.getDad(), familyTree);
                 } else if (familyMember.getDad().getStatus() == Status.PROTECTED && isInFamilyTree(currentUser, familyMember.getDad())) {
                     getParentsList(currentUser, familyMember.getDad(), familyTree);
                 }
             }
         }
    }

    public void getChildList(FamilyMember currentUser,FamilyMember familyMember, List<FamilyMember> familyTree){
        for (FamilyMember familyMember1 : getAll()){
            if(familyMember==familyMember1.getDad()){
                if(familyMember1.getDad().getStatus()==Status.PRIVATE && familyMember1.getDad()==currentUser){
                    familyTree.add(familyMember1);
                    getChildList(currentUser,familyMember1.getDad(),familyTree);
                } else if (familyMember1.getDad().getStatus()==Status.PUBLIC) {
                    familyTree.add(familyMember1);
                    getChildList(currentUser,familyMember1.getDad(),familyTree);
                } else if (familyMember1.getDad().getStatus()==Status.PROTECTED && isInFamilyTree(currentUser,familyMember1.getDad()) ) {
                    familyTree.add(familyMember1);
                    getChildList(currentUser,familyMember1.getDad(),familyTree);
                }
            }
            if(familyMember == familyMember1.getMom()){
                if(familyMember1.getMom().getStatus() == Status.PRIVATE && familyMember1.getMom() == currentUser){
                    familyTree.add(familyMember1);
                    getChildList(currentUser, familyMember1.getMom(), familyTree);
                } else if (familyMember1.getMom().getStatus() == Status.PUBLIC) {
                    familyTree.add(familyMember1);
                    getChildList(currentUser, familyMember1.getMom(), familyTree);
                } else if (familyMember1.getMom().getStatus() == Status.PROTECTED && isInFamilyTree(currentUser, familyMember1.getMom())) {
                    familyTree.add(familyMember1);
                    getChildList(currentUser, familyMember1.getMom(), familyTree);
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
