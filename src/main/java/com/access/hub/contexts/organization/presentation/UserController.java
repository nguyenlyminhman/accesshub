package com.access.hub.contexts.organization.presentation;

import com.access.hub.contexts.organization.application.dto.CreateUserDto;
import com.access.hub.contexts.organization.application.usecase.CreateUserUseCase;
import com.access.hub.contexts.organization.domain.entity.Department;
import com.access.hub.contexts.organization.domain.entity.User;
import com.access.hub.shared.application.dto.ResponseObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/organization/user")
@Tag(name = "Organization Context", description = "Quản lý phòng ban và nhân sự")
public class UserController {
    private final CreateUserUseCase createUserUseCase;

    public UserController (CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    @PostMapping()
    public ResponseObject createUser(@RequestBody CreateUserDto request) {
        ResponseObject responseObject = new ResponseObject();

        String mockCurrentUserId = "admin_01";
        User rs = createUserUseCase.execute(request, mockCurrentUserId);

        responseObject.setSuccess(true);
        responseObject.setCode(201);
        responseObject.setMessage("Created");
        responseObject.setData(rs);

        return responseObject;
    }
}
