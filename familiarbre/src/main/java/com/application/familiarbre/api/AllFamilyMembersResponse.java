package com.application.familiarbre.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class AllFamilyMembersResponse {
    private List<PublicFamilyMember> familyMembers;
}
