package com.application.familiarbre.models.helper;

import com.application.familiarbre.models.entites.FamilyMember;
import com.application.familiarbre.models.entites.Node;
import com.application.familiarbre.models.entites.Status;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class FamilyTreeHelper {

    public static List<Node> getFamilyTree(FamilyMember currentUser, FamilyMember familyMember, List<FamilyMember> allFamilyMembers) {
        List<FamilyMember> familyTree = new ArrayList<FamilyMember>();
        getParentsList(currentUser, familyMember, familyTree);
        getChildList(currentUser, familyMember, familyTree, allFamilyMembers);
        List<Node> tree = new ArrayList<>();
        for (FamilyMember i : familyTree) {
            Long midId = (i.getMid() != null && i.getMid().getStatus() != Status.PRIVATE) ? i.getMid().getId() : null;
            Long fidId = (i.getFid() != null && i.getFid().getStatus() != Status.PRIVATE) ? i.getFid().getId() : null;
            List<Long> pidsList = new ArrayList<>();

            if (i.getPids().size() != 0) {
                for (FamilyMember j : i.getPids()) {

                    pidsList.add(j.getId());
                    Long[] iIdArray = new Long[]{i.getId()};
                    Date birthdayDate = j.getBirthDay();

                    String birthday = "";
                    if (birthdayDate != null) {
                        birthday = birthdayDate.toString();
                    }

                    tree.add(
                            Node.builder()
                                    .id(j.getId())
                                    .mid(null)
                                    .pids(iIdArray)
                                    .fid(null)
                                    .name(j.getFullName())
                                    .gender(j.getGender())
                                    .socialSecurityNumber(j.getSocialSecurityNumber())
                                    .birthDay(birthday)
                                    .build()
                    );
                }
            }

            Long[] iPids = pidsList.toArray(new Long[0]);
            Date birthdayDate = i.getBirthDay();

            String birthday = "";
            if (birthdayDate != null) {
                birthday = birthdayDate.toString();
            }

            tree.add(
                    Node.builder()
                            .id(i.getId())
                            .mid(midId)
                            .pids(iPids)
                            .fid(fidId)
                            .name(i.getFullName())
                            .gender(i.getGender())
                            .socialSecurityNumber(i.getSocialSecurityNumber())
                            .birthDay(birthday)
                            .build()
            );
        }
        return tree;
    }

    public static void getParentsList(FamilyMember currentUser, FamilyMember familyMember, List<FamilyMember> familyTree) {
        if (familyMember != null) {
            familyTree.add(familyMember);
            if (familyMember.getMid() != null) {
                if ((familyMember.getMid().getStatus() == Status.PRIVATE) && familyMember.getMid() == currentUser) {
                    getParentsList(currentUser, familyMember.getMid(), familyTree);
                } else if (familyMember.getMid().getStatus() == Status.PUBLIC | familyMember.getMid().getStatus() == null) {
                    getParentsList(currentUser, familyMember.getMid(), familyTree);
                } else if (familyMember.getMid().getStatus() == Status.PROTECTED && (isInFamilyTree(currentUser, familyMember.getMid()) | isInFamilyTree(familyMember.getMid(), currentUser))) {
                    getParentsList(currentUser, familyMember.getMid(), familyTree);
                }
            }
            if (familyMember.getFid() != null) {
                if ((familyMember.getFid().getStatus() == Status.PRIVATE) && familyMember.getFid() == currentUser) {
                    getParentsList(currentUser, familyMember.getFid(), familyTree);
                } else if (familyMember.getFid().getStatus() == Status.PUBLIC | familyMember.getFid().getStatus() == null) {
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
        } else if (user1 == null || user2 == null) {
            return false;
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

    public static void addChild(FamilyMember currentUser, List<FamilyMember> familyTree, FamilyMember child, List<FamilyMember> allFamilyMembers) {
        if ((child.getStatus() == Status.PRIVATE) && child == currentUser) {
            familyTree.add(child);
            getChildList(currentUser, child, familyTree, allFamilyMembers);
        } else if (child.getStatus() == Status.PUBLIC | child.getStatus() == null) {
            familyTree.add(child);
            getChildList(currentUser, child, familyTree, allFamilyMembers);
        } else if (child.getStatus() == Status.PROTECTED && (isInFamilyTree(currentUser, child)) | isInFamilyTree(child, currentUser)) {
            familyTree.add(child);
            getChildList(currentUser, child, familyTree, allFamilyMembers);
        }
    }
}
