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
public class CreateMenuDto {
    private int projectId;
    private Integer parentId;
    private String url;
    private String title;
    private String details;
    private int sortOrder;
    private List<Integer> permissionIds;
 }
