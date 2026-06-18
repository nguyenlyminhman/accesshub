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
public class MenuSharedDTO {
    private int menuId;
    private Integer parentId;
    private String menuTitle;
    private String menuUrl;
    private int sortOrder;
    private String permissions;
}
