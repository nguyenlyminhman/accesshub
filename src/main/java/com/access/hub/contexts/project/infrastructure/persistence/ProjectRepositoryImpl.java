package com.access.hub.contexts.project.infrastructure.persistence;

import com.access.hub.contexts.project.domain.entity.Project;
import com.access.hub.contexts.project.domain.repository.ProjectRepository;
import com.access.hub.contexts.project.infrastructure.persistence.jpa.SpringDataProjectRepository;
import org.springframework.stereotype.Component;

@Component
public class ProjectRepositoryImpl implements ProjectRepository {
    private final SpringDataProjectRepository projectRepository;

    public ProjectRepositoryImpl(SpringDataProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public boolean existsByCode(String code) {
        return projectRepository.existsByCode(code);
    }
}
