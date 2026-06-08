package com.access.hub.contexts.project.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProjectDto {
    private String code;
    private String url;
    private String name;
    private String details;
    private String status;
}
