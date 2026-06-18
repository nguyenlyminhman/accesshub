package com.access.hub.contexts.project.infrastructure.bridge;

import com.access.hub.contexts.auth.application.port.AuthProjectService;
import com.access.hub.contexts.auth.application.port.dto.MenuSharedDTO;
import com.access.hub.contexts.project.application.dto.MenuInfoProjection;
import com.access.hub.contexts.project.domain.repository.ProjectRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthProjectBridge implements AuthProjectService {

    private final ProjectRepository projectRepository;

    public AuthProjectBridge(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<MenuSharedDTO> findRawMenusByUsernameAndProject(String username, String projectCode) {

            List<MenuInfoProjection> menuList = projectRepository.findRawMenusByUsernameAndProject(username, projectCode);
            List<MenuSharedDTO> sharedDTOList = menuList.stream().map(e -> new MenuSharedDTO(
                    e.getMenuId(),
                    e.getParentId(),
                    e.getMenuTitle(),
                    e.getMenuUrl(),
                    e.getSortOrder(),
                    e.getPermissions()
            )).toList();

        return sharedDTOList;
    }
}
