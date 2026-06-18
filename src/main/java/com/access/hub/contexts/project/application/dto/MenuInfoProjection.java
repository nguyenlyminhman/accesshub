package com.access.hub.contexts.project.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuInfoProjection {
    private int menuId;
    private Integer parentId;
    private String menuTitle;
    private String menuUrl;
    private String uiCode;
    private int sortOrder;
    private String permissions;
}
