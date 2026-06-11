package com.access.hub.contexts.organization.infrastructure.bridge;

import com.access.hub.contexts.auth.application.port.AuthOrganizationService;
import com.access.hub.contexts.auth.application.port.dto.UserSharedDTO;
import com.access.hub.contexts.organization.application.dto.UserInfoProjection;
import com.access.hub.contexts.organization.domain.entity.User;
import com.access.hub.contexts.organization.domain.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthOrganizationBridge implements AuthOrganizationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthOrganizationBridge(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean verifyCredentials(String username, String rawPassword, String projectCode) {
        User user = userRepository.findByUsername(username);
        if (user == null) return false;

        String password = user.getPassword();
        Boolean rs = passwordEncoder.matches(rawPassword, password);
        if (!rs) return false;

        return true;
    }

    @Override
    public UserSharedDTO getUserDetails(String username, String projectCode) {
        UserSharedDTO userSharedDTO = new UserSharedDTO();

        UserInfoProjection userInfoProjection = userRepository.getUserDepartmentDetails(username);
        List<String> roleList = userRepository.findRolesByUsernameAndProjectCode(username, projectCode);

        userSharedDTO.setUsername(userInfoProjection.getUsername());
        userSharedDTO.setEmail(userInfoProjection.getEmail().getValue());
        userSharedDTO.setDeptCode(userInfoProjection.getDeptCode());
        userSharedDTO.setDeptName(userInfoProjection.getDeptName());
        userSharedDTO.setDetails(userInfoProjection.getDetails());
        userSharedDTO.setRoles(roleList);

        return userSharedDTO;
    }
}
