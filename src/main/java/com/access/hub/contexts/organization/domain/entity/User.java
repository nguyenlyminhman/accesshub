package com.access.hub.contexts.organization.domain.entity;

import com.access.hub.contexts.organization.domain.valueobject.EmailVO;
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
@Table(name="users", schema = "public")
public class User extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "dept_id")
    private int deptId;

    private String username;

    @Embedded
    private EmailVO email;

    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}
