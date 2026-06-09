package com.access.hub.contexts.organization.application.dto;

import com.access.hub.contexts.organization.domain.valueobject.EmailVO;
import com.access.hub.shared.domain.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {
    private int deptId;
    private String username;
    private EmailVO email;
    private String password;
}
