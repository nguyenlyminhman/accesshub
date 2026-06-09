package com.access.hub.contexts.project.domain.valueobject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProjectVO {
    private String code;
    private String url;
    private String name;
    private String details;
    private String status;
}
