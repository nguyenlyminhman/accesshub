package com.access.hub.contexts.organization.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateDepartmentDto {
    private String deptCode;
    private String deptName;
    private String details;
}
