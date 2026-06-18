package com.access.hub.contexts.project.application.usecase;

import com.access.hub.contexts.project.application.dto.CreateProjectDto;
import com.access.hub.contexts.project.domain.entity.Project;
import com.access.hub.contexts.project.domain.repository.ProjectRepository;
import com.access.hub.shared.Status;
import com.access.hub.shared.domain.exception.DomainException;
import org.springframework.stereotype.Service;

@Service
public class CreateProjectUseCase {

    private final ProjectRepository projectRepository;

    public CreateProjectUseCase (ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project execute(CreateProjectDto request, String currentUserId) {

        if (projectRepository.existsByPrjCode(request.getCode())) {
            throw new DomainException("Project code '" + request.getCode() + "' already exists!");
        }

        Project project = new Project();
        project.setPrjCode(request.getCode());
        project.setPrjName(request.getName());
        project.setDetails(request.getDetails());
        project.setStatus(Status.ACTIVE);
        project.markAsCreated(currentUserId);

        return projectRepository.save(project);
    }
}
