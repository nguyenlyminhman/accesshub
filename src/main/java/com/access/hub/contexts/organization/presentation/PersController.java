package com.access.hub.contexts.organization.presentation;

import com.access.hub.contexts.organization.application.dto.CreateUserDto;
import com.access.hub.contexts.organization.application.usecase.CreateUserUseCase;
import com.access.hub.contexts.organization.application.usecase.GetAllPermissionsUseCase;
import com.access.hub.contexts.organization.domain.entity.Permission;
import com.access.hub.contexts.organization.domain.entity.User;
import com.access.hub.shared.application.dto.ResponseObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/organization/permission")
@Tag(name = "Organization Context", description = "Quản lý phòng ban và nhân sự")
public class PersController {
    private final GetAllPermissionsUseCase getAllPermissionsUseCase;

    public PersController(GetAllPermissionsUseCase getAllPermissionsUseCase) {
        this.getAllPermissionsUseCase = getAllPermissionsUseCase;
    }

    @GetMapping
    public ResponseObject getAllPermissions() {
        ResponseObject responseObject = new ResponseObject();

        List<Permission> rs = getAllPermissionsUseCase.execute();

        responseObject.setSuccess(true);
        responseObject.setCode(HttpStatus.ACCEPTED.value());
        responseObject.setMessage("Success");
        responseObject.setData(rs);

        return responseObject;
    }
}
