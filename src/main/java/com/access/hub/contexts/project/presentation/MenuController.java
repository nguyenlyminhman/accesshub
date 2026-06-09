package com.access.hub.contexts.project.presentation;

import com.access.hub.contexts.project.application.dto.CreateMenuDto;
import com.access.hub.contexts.project.application.dto.CreateProjectDto;
import com.access.hub.contexts.project.application.usecase.CreateMenuUseCase;
import com.access.hub.contexts.project.application.usecase.CreateProjectUseCase;
import com.access.hub.contexts.project.domain.entity.Menu;
import com.access.hub.contexts.project.domain.entity.Project;
import com.access.hub.shared.application.dto.ResponseObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/project/menu")
@Tag(name = "Project Context", description = "Quản lý dự án và phân bổ tài nguyên")
public class MenuController {

    private final CreateMenuUseCase createMenuUseCase;

    public MenuController(CreateMenuUseCase createMenuUseCase) {
        this.createMenuUseCase = createMenuUseCase;
    }

    @PostMapping()
    public ResponseObject createMenu(@RequestBody CreateMenuDto request) {
        ResponseObject responseObject = new ResponseObject();

        String mockCurrentUserId = "admin_01";
        Menu rs = createMenuUseCase.execute(request, mockCurrentUserId);

        responseObject.setSuccess(true);
        responseObject.setCode(201);
        responseObject.setMessage("Created");
        responseObject.setData(rs);

        return responseObject;
    }
}
