package com.application.familiarbre.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class AddChildRequest {
    private Long parentId;
    private Long childId;
}
