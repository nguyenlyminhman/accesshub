package com.access.hub.contexts.project.presentation;

import com.access.hub.contexts.project.application.dto.CreateProjectDto;
import com.access.hub.contexts.project.application.usecase.CreateProjectUseCase;
import com.access.hub.contexts.project.domain.entity.Project;
import com.access.hub.shared.application.dto.ResponseObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private final CreateProjectUseCase createProjectUseCase;

    public ProjectController(CreateProjectUseCase createProjectUseCase) {
        this.createProjectUseCase = createProjectUseCase;
    }

    @PostMapping
    public ResponseObject createProject(@RequestBody CreateProjectDto request) {
        ResponseObject responseObject = new ResponseObject();

        String mockCurrentUserId = "admin_01";
        Project createdProject = createProjectUseCase.execute(request, mockCurrentUserId);

        responseObject.setSuccess(true);
        responseObject.setCode(201);
        responseObject.setMessage("Created");
        responseObject.setData(createdProject);

        return responseObject;
    }
}
