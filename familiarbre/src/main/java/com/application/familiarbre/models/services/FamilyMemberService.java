package com.application.familiarbre.models.services;

import com.application.familiarbre.models.dao.FamilyMemberRepository;
import com.application.familiarbre.models.entites.FamilyMember;
import com.application.familiarbre.models.entites.Status;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
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
                 if((familyMember.getMom().getStatus() == Status.PRIVATE | familyMember.getMom().getStatus()==null )&& familyMember.getMom() == currentUser){
                     getParentsList(currentUser, familyMember.getMom(), familyTree);
                 } else if (familyMember.getMom().getStatus() == Status.PUBLIC) {
                     getParentsList(currentUser, familyMember.getMom(), familyTree);
                 } else if (familyMember.getMom().getStatus() == Status.PROTECTED && (isInFamilyTree(currentUser, familyMember.getMom())|isInFamilyTree(familyMember.getMom(),currentUser))) {
                     getParentsList(currentUser, familyMember.getMom(), familyTree);
                 }
             }
             if (familyMember.getDad()!=null){
                 if ((familyMember.getDad().getStatus() == Status.PRIVATE| familyMember.getDad().getStatus()==null ) && familyMember.getDad() == currentUser) {
                     getParentsList(currentUser, familyMember.getDad(), familyTree);
                 } else if (familyMember.getDad().getStatus() == Status.PUBLIC) {
                     getParentsList(currentUser, familyMember.getDad(), familyTree);
                 } else if (familyMember.getDad().getStatus() == Status.PROTECTED && (isInFamilyTree(currentUser, familyMember.getDad())|isInFamilyTree(familyMember.getDad(),currentUser)) ){
                     getParentsList(currentUser, familyMember.getDad(), familyTree);
                 }
             }
         }
    }

    public void getChildList(FamilyMember currentUser,FamilyMember familyMember, List<FamilyMember> familyTree){
        for (FamilyMember familyMember1 : getAll()){
            if(familyMember==familyMember1.getDad()){
                addChild(currentUser, familyTree, familyMember1);
            }
            if(familyMember == familyMember1.getMom()){
                addChild(currentUser, familyTree, familyMember1);
            }

        }
    }

    private void addChild(FamilyMember currentUser, List<FamilyMember> familyTree, FamilyMember familyMember1) {
        if((familyMember1.getStatus() == Status.PRIVATE | familyMember1.getStatus()==null ) && familyMember1 == currentUser){
            familyTree.add(familyMember1);
            getChildList(currentUser, familyMember1, familyTree);
        } else if (familyMember1.getStatus() == Status.PUBLIC) {
            familyTree.add(familyMember1);
            getChildList(currentUser, familyMember1, familyTree);
        } else if (familyMember1.getStatus() == Status.PROTECTED && (isInFamilyTree(currentUser, familyMember1)) | isInFamilyTree(familyMember1,currentUser)){
            familyTree.add(familyMember1);
            getChildList(currentUser, familyMember1, familyTree);
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
