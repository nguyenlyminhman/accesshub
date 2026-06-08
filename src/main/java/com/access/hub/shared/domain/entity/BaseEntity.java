package com.access.hub.shared.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity {
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    public void markAsCreated(String userId) {
        this.createdAt = LocalDateTime.now();
        this.createdBy = userId;
    }

    public void markAsUpdated(String userId) {
        this.updatedAt = LocalDateTime.now();
        this.updatedBy = userId;
    }
}
