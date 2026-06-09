package com.access.hub.contexts.project.infrastructure.persistence.jpa;

import com.access.hub.contexts.project.domain.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProjectRepository extends JpaRepository<Project, Integer> {
    boolean existsByCode(String code);
}
