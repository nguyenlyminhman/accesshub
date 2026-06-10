package com.access.hub.contexts.auth.presentation;

import com.access.hub.contexts.auth.application.dto.LoginReqDTO;
import com.access.hub.shared.application.dto.ResponseObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth Context", description = "Các API phục vụ Stateless Authentication, JWT Issuance & Refresh Token")
public class VerifyController {


    @PostMapping("/verify")
    public ResponseObject verifyUser() {

        return null;
    }
}
