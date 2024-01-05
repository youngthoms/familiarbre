package com.application.familiarbre.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class UpdateRequest {
    Long fid;
    String gender;
    Long mid;
    String name;
    Long []pids;
    String socialSecurityNumber;
}
