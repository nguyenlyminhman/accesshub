package com.access.hub.contexts.organization.domain.valueobject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserVO {
    private int deptId;
    private String username;
    private String email;
    private String password;
    private String status;
}
