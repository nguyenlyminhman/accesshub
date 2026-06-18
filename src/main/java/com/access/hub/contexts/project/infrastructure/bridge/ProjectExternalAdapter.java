package com.access.hub.contexts.project.infrastructure.bridge;

import com.access.hub.contexts.project.domain.service.ProjectService;
import com.access.hub.contexts.project.infrastructure.persistence.dao.ProjectDao;
import org.springframework.stereotype.Service;

@Service
public class ProjectExternalAdapter implements ProjectService {

    private final ProjectDao projectRepository;

    public ProjectExternalAdapter(ProjectDao projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public boolean isProjectActive(String projectCode) {
        return projectRepository.existsByPrjCode(projectCode);
    }
}
