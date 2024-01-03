package com.application.familiarbre.models.services;

import com.application.familiarbre.models.dao.FamilyMemberRepository;
import com.application.familiarbre.models.entites.FamilyMember;
import com.application.familiarbre.models.entites.Node;
import com.application.familiarbre.models.entites.Status;
import com.application.familiarbre.models.entites.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Optional<FamilyMember> getByUser(User user) {
        return repository.findByUser(user);
    }
    public List<Node> hasAccess(Long idCurrentUser, Long idUserTarget){
        if (getById(idUserTarget).getStatus()==Status.PUBLIC){
            return getFamilyTree(idCurrentUser, idUserTarget);
        }
        else if (getById(idUserTarget).getStatus()==Status.PROTECTED && (isInFamilyTree(getById(idCurrentUser),getById(idCurrentUser))|isInFamilyTree(getById(idUserTarget),getById(idCurrentUser)))){
            return getFamilyTree(idCurrentUser, idUserTarget);
        } else if ((getById(idUserTarget).getStatus()==Status.PRIVATE | getById(idUserTarget).getStatus()==null) && idUserTarget==idCurrentUser) {
            return getFamilyTree(idCurrentUser, idUserTarget);
        }
        throw new org.springframework.security.access.AccessDeniedException("403 returned");
    }
    public List<Node> getFamilyTree(long idCurrentUser, long idUserTarget){
        return getFamilyTree(getById(idCurrentUser),getById(idUserTarget));
    }
    public List<Node> getFamilyTree(FamilyMember currentUser,FamilyMember familyMember){
        List<FamilyMember> familyTree=new ArrayList<FamilyMember>();
        getParentsList(currentUser,familyMember,familyTree);
        getChildList(currentUser,familyMember,familyTree);
        List<Node> tree = new ArrayList<>();
        for (FamilyMember i : familyTree) {
            Long midId = (i.getMid() != null) ? i.getMid().getId() : null;
            Long fidId = (i.getFid() != null) ? i.getFid().getId() : null;

            List<Long> pids = new ArrayList<>();
            for (FamilyMember j : i.getPids()) {
                pids.add(j.getId());
            }
            Long[] pidsArray = pids.toArray(new Long[0]);

            tree.add(new Node(i.getId(), midId, pidsArray, fidId, i.getFullName(), i.getGender()));
        }
        return tree;
    }

    public void getParentsList (FamilyMember currentUser, FamilyMember familyMember, List<FamilyMember> familyTree){
         if(familyMember != null){
             familyTree.add(familyMember);
             if (familyMember.getMid()!=null){
                 if((familyMember.getMid().getStatus() == Status.PRIVATE | familyMember.getMid().getStatus()==null )&& familyMember.getMid() == currentUser){
                     getParentsList(currentUser, familyMember.getMid(), familyTree);
                 } else if (familyMember.getMid().getStatus() == Status.PUBLIC) {
                     getParentsList(currentUser, familyMember.getMid(), familyTree);
                 } else if (familyMember.getMid().getStatus() == Status.PROTECTED && (isInFamilyTree(currentUser, familyMember.getMid())|isInFamilyTree(familyMember.getMid(),currentUser))) {
                     getParentsList(currentUser, familyMember.getMid(), familyTree);
                 }
             }
             if (familyMember.getFid()!=null){
                 if ((familyMember.getFid().getStatus() == Status.PRIVATE| familyMember.getFid().getStatus()==null ) && familyMember.getFid() == currentUser) {
                     getParentsList(currentUser, familyMember.getFid(), familyTree);
                 } else if (familyMember.getFid().getStatus() == Status.PUBLIC) {
                     getParentsList(currentUser, familyMember.getFid(), familyTree);
                 } else if (familyMember.getFid().getStatus() == Status.PROTECTED && (isInFamilyTree(currentUser, familyMember.getFid())|isInFamilyTree(familyMember.getFid(),currentUser)) ){
                     getParentsList(currentUser, familyMember.getFid(), familyTree);
                 }
             }
         }
    }

    public void getChildList(FamilyMember currentUser,FamilyMember familyMember, List<FamilyMember> familyTree){
        for (FamilyMember familyMember1 : getAll()){
            if(familyMember==familyMember1.getFid()){
                addChild(currentUser, familyTree, familyMember1);
            }
            if(familyMember == familyMember1.getMid()){
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

            if (user2.getMid()!=null){
                momResult=isInFamilyTree(user1,user2.getMid());
            }
            if (user2.getFid()!=null) {
                dadResult=isInFamilyTree(user1, user2.getFid());
            }
            return momResult || dadResult;
        }
    }

    public FamilyMember getByUserId(Long userId) {
        Optional<FamilyMember> fm = repository.findByUserId(userId);
        return fm.orElse(null);
    }

}
