package com.access.hub.contexts.auth.application.port;

import com.access.hub.contexts.auth.application.port.dto.MenuSharedDTO;

import java.util.List;

public interface AuthProjectService {
    List<MenuSharedDTO> findRawMenusByUsernameAndProject(String username, String projectCode);
}
