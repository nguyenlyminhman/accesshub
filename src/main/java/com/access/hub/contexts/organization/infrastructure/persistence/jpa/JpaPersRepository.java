package com.access.hub.contexts.organization.infrastructure.persistence.jpa;

import com.access.hub.contexts.organization.domain.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPersRepository extends JpaRepository<Permission, Integer> {
}
