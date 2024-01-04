package com.application.familiarbre.models.helper;

import com.application.familiarbre.models.entites.FamilyMember;
import com.application.familiarbre.models.entites.Node;
import com.application.familiarbre.models.entites.Status;

import java.util.ArrayList;
import java.util.List;

public class FamilyTreeHelper {

    public static List<Node> getFamilyTree(FamilyMember currentUser, FamilyMember familyMember, List<FamilyMember> allFamilyMembers) {
        List<FamilyMember> familyTree = new ArrayList<FamilyMember>();
        getParentsList(currentUser, familyMember, familyTree);
        getChildList(currentUser, familyMember, familyTree, allFamilyMembers);
        List<Node> tree = new ArrayList<>();
        for (FamilyMember i : familyTree) {
            Long midId = (i.getMid() != null) ? i.getMid().getId() : null;
            Long fidId = (i.getFid() != null) ? i.getFid().getId() : null;

            List<Long> pids = new ArrayList<>();
            if (i.getPids() != null) {
                for (FamilyMember j : i.getPids()) {
                    pids.add(j.getId());
                }
            }
            Long[] pidsArray = pids.toArray(new Long[0]);

            tree.add(new Node(i.getId(), midId, pidsArray, fidId, i.getFullName(), i.getGender()));
        }
        return tree;
    }

    public static void getParentsList(FamilyMember currentUser, FamilyMember familyMember, List<FamilyMember> familyTree) {
        if (familyMember != null) {
            familyTree.add(familyMember);
            if (familyMember.getMid() != null) {
                if ((familyMember.getMid().getStatus() == Status.PRIVATE | familyMember.getMid().getStatus() == null) && familyMember.getMid() == currentUser) {
                    getParentsList(currentUser, familyMember.getMid(), familyTree);
                } else if (familyMember.getMid().getStatus() == Status.PUBLIC) {
                    getParentsList(currentUser, familyMember.getMid(), familyTree);
                } else if (familyMember.getMid().getStatus() == Status.PROTECTED && (isInFamilyTree(currentUser, familyMember.getMid()) | isInFamilyTree(familyMember.getMid(), currentUser))) {
                    getParentsList(currentUser, familyMember.getMid(), familyTree);
                }
            }
            if (familyMember.getFid() != null) {
                if ((familyMember.getFid().getStatus() == Status.PRIVATE | familyMember.getFid().getStatus() == null) && familyMember.getFid() == currentUser) {
                    getParentsList(currentUser, familyMember.getFid(), familyTree);
                } else if (familyMember.getFid().getStatus() == Status.PUBLIC) {
                    getParentsList(currentUser, familyMember.getFid(), familyTree);
                } else if (familyMember.getFid().getStatus() == Status.PROTECTED && (isInFamilyTree(currentUser, familyMember.getFid()) | isInFamilyTree(familyMember.getFid(), currentUser))) {
                    getParentsList(currentUser, familyMember.getFid(), familyTree);
                }
            }
        }
    }

    public static void getChildList(FamilyMember currentUser, FamilyMember familyMember, List<FamilyMember> familyTree, List<FamilyMember> allFamilyMembers) {
        for (FamilyMember familyMember1 : allFamilyMembers) {
            if (familyMember == familyMember1.getFid()) {
                addChild(currentUser, familyTree, familyMember1, allFamilyMembers);
            }
            if (familyMember == familyMember1.getMid()) {
                addChild(currentUser, familyTree, familyMember1, allFamilyMembers);
            }

        }
    }

    public static boolean isInFamilyTree(FamilyMember user1, FamilyMember user2) {
        if (user1 == user2) {
            return true;
        } else {
            boolean momResult = false;
            boolean dadResult = false;

            if (user2.getMid() != null) {
                momResult = isInFamilyTree(user1, user2.getMid());
            }
            if (user2.getFid() != null) {
                dadResult = isInFamilyTree(user1, user2.getFid());
            }
            return momResult || dadResult;
        }
    }

    public static void addChild(FamilyMember currentUser, List<FamilyMember> familyTree, FamilyMember familyMember1, List<FamilyMember> allFamilyMembers) {
        if ((familyMember1.getStatus() == Status.PRIVATE | familyMember1.getStatus() == null) && familyMember1 == currentUser) {
            familyTree.add(familyMember1);
            getChildList(currentUser, familyMember1, familyTree, allFamilyMembers);
        } else if (familyMember1.getStatus() == Status.PUBLIC) {
            familyTree.add(familyMember1);
            getChildList(currentUser, familyMember1, familyTree, allFamilyMembers);
        } else if (familyMember1.getStatus() == Status.PROTECTED && (isInFamilyTree(currentUser, familyMember1)) | isInFamilyTree(familyMember1, currentUser)) {
            familyTree.add(familyMember1);
            getChildList(currentUser, familyMember1, familyTree, allFamilyMembers);
        }
    }
}
