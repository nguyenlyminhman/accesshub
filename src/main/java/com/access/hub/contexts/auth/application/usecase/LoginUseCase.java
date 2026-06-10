package com.access.hub.contexts.auth.application.usecase;

import com.access.hub.contexts.auth.application.dto.LoginReqDTO;
import com.access.hub.contexts.auth.application.port.AuthOrganizationService;
import com.access.hub.contexts.auth.application.port.AuthProjectService;
import com.access.hub.contexts.auth.application.port.dto.MenuSharedDTO;
import com.access.hub.contexts.auth.application.port.dto.UserSharedDTO;
import com.access.hub.shared.domain.exception.DomainException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginUseCase {

    private final AuthOrganizationService authOrganizationService;
    private final AuthProjectService authProjectService;


    public LoginUseCase(AuthOrganizationService authOrganizationService, AuthProjectService authProjectService) {
        this.authOrganizationService = authOrganizationService;
        this.authProjectService = authProjectService;
    }


    public UserSharedDTO execute(LoginReqDTO request) {
        UserSharedDTO userSharedDTO = new UserSharedDTO();
        boolean rs = authOrganizationService.verifyCredentials(request.getUsername(), request.getPassword(), request.getProjectCode());

        if (!rs) throw new DomainException("Wrong credential");

        userSharedDTO = authOrganizationService.getUserDetails(request.getUsername(), request.getProjectCode());
        List<MenuSharedDTO> sharedDTOList = authProjectService.findRawMenusByUsernameAndProject(request.getUsername(), request.getProjectCode());
        userSharedDTO.setMenuInfo(sharedDTOList);



        return userSharedDTO;
    }
}
