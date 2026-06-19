package com.access.hub.contexts.organization.presentation;

import com.access.hub.contexts.organization.application.dto.CreateDepartmentDto;
import com.access.hub.contexts.organization.application.usecase.CreateDepartmentUseCase;
import com.access.hub.contexts.organization.application.usecase.GetDepartmentUseCase;
import com.access.hub.contexts.organization.domain.entity.Department;
import com.access.hub.shared.application.dto.ResponseObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/organization/department")
@Tag(name = "Organization Context", description = "Quản lý phòng ban và nhân sự")
public class DepartmentController {

    private final CreateDepartmentUseCase createDepartmentUseCase;
    private final GetDepartmentUseCase getDepartmentUseCase;

    public DepartmentController(CreateDepartmentUseCase createDepartmentUseCase, GetDepartmentUseCase getDepartmentUseCase) {
        this.createDepartmentUseCase = createDepartmentUseCase;
        this.getDepartmentUseCase = getDepartmentUseCase;
    }

    @PostMapping()
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

    @GetMapping()
    public ResponseObject getAllDept() {
        ResponseObject responseObject = new ResponseObject();

        List<Department> rs = getDepartmentUseCase.execute();

        responseObject.setSuccess(true);
        responseObject.setCode(200);
        responseObject.setMessage("Success");
        responseObject.setData(rs);

        return responseObject;
    }
}
