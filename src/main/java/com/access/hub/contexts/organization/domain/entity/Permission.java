package com.access.hub.contexts.organization.domain.entity;

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
@Table(name="permissions", schema = "public")
public class Permission extends BaseEntity {
    @Id
    @Column(name = "id")
    private int id;

    private String code;

    private String details;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}
