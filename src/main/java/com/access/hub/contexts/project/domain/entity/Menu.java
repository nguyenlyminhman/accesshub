package com.access.hub.contexts.project.domain.entity;

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
@Table(name = "menus", schema = "public")
public class Menu extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "project_id")
    private int projectId;

    @Column(name = "parent_id")
    private Integer parentId;

    private String url;
    private String title;
    private String details;

    @Column(name = "sort_order")
    private int sortOrder;
}
