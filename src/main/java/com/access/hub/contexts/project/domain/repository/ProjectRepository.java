package com.access.hub.contexts.project.domain.repository;

import com.access.hub.contexts.project.domain.entity.Project;

public interface ProjectRepository {
    Project save(Project project);
    boolean existsByCode(String code);
}
