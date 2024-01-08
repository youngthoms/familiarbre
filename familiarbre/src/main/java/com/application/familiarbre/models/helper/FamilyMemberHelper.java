package com.application.familiarbre.models.helper;

import com.application.familiarbre.api.PublicFamilyMember;
import com.application.familiarbre.models.entites.FamilyMember;

import java.util.ArrayList;
import java.util.List;

public class FamilyMemberHelper {
    private static PublicFamilyMember familyMemberToPublicFamilyMember(FamilyMember fm) {
        return PublicFamilyMember
                .builder()
                .id(fm.getId())
                .fullName(fm.getFullName())
                .status(fm.getStatus())
                .gender(fm.getGender())
                .build();
    }

    public static List<PublicFamilyMember> familyMembersToPublicFamilyMembers(List<FamilyMember> fms) {
        List<PublicFamilyMember> publicFamilyMembers = new ArrayList<PublicFamilyMember>();

        for (FamilyMember fm : fms) {
            publicFamilyMembers.add(familyMemberToPublicFamilyMember(fm));
        }

        return publicFamilyMembers;
    }
}
