package com.access.hub.contexts.project.domain.repository;

import com.access.hub.contexts.project.application.dto.MenuInfoProjection;
import com.access.hub.contexts.project.domain.entity.Project;

import java.util.List;

public interface ProjectRepository {
    Project save(Project project);
    boolean existsByPrjCode(String code);

    List<MenuInfoProjection> findRawMenusByUsernameAndProject(String username, String projectCode);
}
