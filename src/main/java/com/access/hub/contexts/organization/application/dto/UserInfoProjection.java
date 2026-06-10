package com.access.hub.contexts.organization.application.dto;

import com.access.hub.contexts.organization.domain.valueobject.EmailVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoProjection {
    private String username;
    private EmailVO email;
    private String deptCode;
    private String deptName;
    private String details;
}
