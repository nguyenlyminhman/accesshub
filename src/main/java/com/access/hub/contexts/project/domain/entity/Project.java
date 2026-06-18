package com.access.hub.contexts.project.domain.entity;

import com.access.hub.shared.Status;
import com.access.hub.shared.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "projects", schema = "public")
public class Project extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "prj_code")
    private String prjCode;

    @Column(name = "prj_name")
    private String prjName;

    private String details;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}
