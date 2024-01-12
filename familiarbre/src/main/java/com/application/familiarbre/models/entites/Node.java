package com.application.familiarbre.models.entites;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Node {
    private Long id;
    private Long mid;
    private Long[] pids;
    private Long fid;
    private String name;
    private Gender gender;
    private String socialSecurityNumber;
    private String birthDay;
}
