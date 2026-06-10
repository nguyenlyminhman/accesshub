package com.access.hub.contexts.organization.infrastructure.persistence.dao;

import com.access.hub.contexts.organization.domain.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermissionDao extends JpaRepository<Permission, Integer> {

    @Query("SELECT p.details FROM Permission p WHERE (p.deletedAt IS NOT NULL OR p.status = '0') and  p.id IN :permissionIds ")
    List<String> findByIdIn(List<Integer> permissionIds);
}
