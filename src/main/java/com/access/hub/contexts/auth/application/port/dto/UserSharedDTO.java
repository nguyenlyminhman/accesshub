package com.access.hub.contexts.auth.application.port.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSharedDTO {
    private String token;
    private UserInfo userInfo;
    private List<MenuSharedDTO> menuInfo;
}

