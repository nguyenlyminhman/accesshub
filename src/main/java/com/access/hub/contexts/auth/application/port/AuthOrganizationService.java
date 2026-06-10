package com.access.hub.contexts.auth.application.port;

import com.access.hub.contexts.auth.application.port.dto.UserSharedDTO;

public interface AuthOrganizationService {
    boolean verifyCredentials(String username, String rawPassword, String projectCode);
    UserSharedDTO getUserDetails(String username, String projectCode);
}
