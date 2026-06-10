package com.access.hub.contexts.auth.presentation;

import com.access.hub.contexts.auth.application.dto.LoginReqDTO;
import com.access.hub.contexts.auth.application.port.dto.UserSharedDTO;
import com.access.hub.contexts.auth.application.usecase.LoginUseCase;
import com.access.hub.shared.application.dto.ResponseObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/v1/auth")
@Tag(name = "Auth Context", description = "Các API phục vụ Stateless Authentication, JWT Issuance & Refresh Token")
public class AuthController {

    private final LoginUseCase loginUseCase;

    public AuthController(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/login")
    public ResponseObject login(@RequestBody LoginReqDTO request) {

        ResponseObject responseObject = new ResponseObject();

        UserSharedDTO userSharedDTO = loginUseCase.execute(request);

        return new ResponseObject("OK", userSharedDTO);
    }

    @PostMapping("/logout")
    public ResponseObject logout() {

        return null;
    }
}
