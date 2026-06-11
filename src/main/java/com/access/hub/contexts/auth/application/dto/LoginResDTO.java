package com.access.hub.contexts.auth.application.dto;

import com.access.hub.contexts.auth.application.port.dto.MenuSharedDTO;
import com.access.hub.contexts.auth.application.port.dto.UserSharedDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResDTO {
    String accessToken;
    UserSharedDTO userInfo;
    List<MenuSharedDTO> menuList;
}
