package com.access.hub.contexts.project.infrastructure.adapter;

import com.access.hub.contexts.project.domain.service.ProjectService;
import com.access.hub.contexts.project.infrastructure.persistence.jpa.JpaProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectExternalAdapter implements ProjectService {

    private final JpaProjectRepository projectRepository;

    public ProjectExternalAdapter(JpaProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public boolean isProjectActive(String projectCode) {
        return projectRepository.existsByCode(projectCode);
    }
}
