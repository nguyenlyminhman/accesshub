package com.access.hub.contexts.organization.presentation;

import com.access.hub.contexts.organization.application.dto.CreateDepartmentDto;
import com.access.hub.contexts.organization.application.usecase.CreateDepartmentUseCase;
import com.access.hub.contexts.organization.domain.entity.Department;
import com.access.hub.contexts.project.domain.entity.Project;
import com.access.hub.shared.application.dto.ResponseObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/department")
public class DepartmentController {

    private final CreateDepartmentUseCase createDepartmentUseCase;

    public DepartmentController(CreateDepartmentUseCase createDepartmentUseCase) {
        this.createDepartmentUseCase = createDepartmentUseCase;
    }

    @PostMapping("/create")
    public ResponseObject createDepartment(@RequestBody CreateDepartmentDto request) {
        ResponseObject responseObject = new ResponseObject();

        String mockCurrentUserId = "admin_01";
        Department rs = createDepartmentUseCase.execute(request, mockCurrentUserId);

        responseObject.setSuccess(true);
        responseObject.setCode(201);
        responseObject.setMessage("Created");
        responseObject.setData(rs);

        return responseObject;
    }

}
