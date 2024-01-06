package com.application.familiarbre.models.entites;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Node {
    private Long id;
    private Long mid;
    private Long[] pids;
    private Long fid;
    private String name;
    private Gender gender;
    private String socialSecurityNumber;
}
