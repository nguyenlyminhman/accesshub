package com.access.hub.contexts.project.application.usecase;

import com.access.hub.contexts.project.application.dto.CreateProjectDto;
import com.access.hub.contexts.project.domain.entity.Project;
import com.access.hub.contexts.project.domain.repository.ProjectRepository;
import com.access.hub.shared.domain.exception.DomainException;
import org.springframework.stereotype.Service;

@Service
public class CreateProjectUseCase {

    private final ProjectRepository projectRepository;

    public CreateProjectUseCase (ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project execute(CreateProjectDto request, String currentUserId) {

        if (projectRepository.existsByCode(request.getCode())) {
            throw new DomainException("Project code '" + request.getCode() + "' already exists!");
        }

        Project project = new Project();
        project.setCode(request.getCode());
        project.setUrl(request.getUrl());
        project.setName(request.getName());
        project.setDetails(request.getDetails());
        project.setStatus("ACTIVE");
        project.markAsCreated(currentUserId);

        return projectRepository.save(project);
    }
}
