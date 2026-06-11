package com.access.hub.contexts.auth.application.usecase;

import com.access.hub.contexts.auth.application.dto.LoginReqDTO;
import com.access.hub.contexts.auth.application.dto.LoginResDTO;
import com.access.hub.contexts.auth.application.port.AuthOrganizationService;
import com.access.hub.contexts.auth.application.port.AuthProjectService;
import com.access.hub.contexts.auth.application.port.AuthTokenService;
import com.access.hub.contexts.auth.application.port.dto.MenuSharedDTO;
import com.access.hub.contexts.auth.application.port.dto.UserSharedDTO;
import com.access.hub.shared.domain.exception.DomainException;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginUseCase {

    private final AuthOrganizationService authOrganizationService;
    private final AuthProjectService authProjectService;
    private final AuthTokenService authTokenService;


    public LoginUseCase(
            AuthOrganizationService authOrganizationService,
            AuthProjectService authProjectService,
            AuthTokenService authTokenService
    ) {
        this.authOrganizationService = authOrganizationService;
        this.authProjectService = authProjectService;
        this.authTokenService = authTokenService;
    }


    public LoginResDTO execute(LoginReqDTO request) {
        LoginResDTO loginResDto = new LoginResDTO();

        boolean rs = authOrganizationService.verifyCredentials(request.getUsername(), request.getPassword(), request.getProjectCode());
        if (!rs) throw new DomainException("Wrong credential");

        UserSharedDTO userSharedDTO = authOrganizationService.getUserDetails(request.getUsername(), request.getProjectCode());
        List<MenuSharedDTO> menuSharedDto = authProjectService.findRawMenusByUsernameAndProject(request.getUsername(), request.getProjectCode());
        String accessToken = authTokenService.createToken(
                request.getUsername(),
                userSharedDTO.getEmail(),
                userSharedDTO.getDeptCode(),
                userSharedDTO.getDeptName(),
                request.getProjectCode()
        );

        loginResDto.setUserInfo(userSharedDTO);
        loginResDto.setMenuList(menuSharedDto);
        loginResDto.setAccessToken(accessToken);

        return loginResDto;
    }
}
