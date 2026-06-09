package com.access.hub.contexts.project.application.usecase;

import com.access.hub.contexts.organization.application.port.OrganizationBridgeService;
import com.access.hub.contexts.project.application.dto.CreateMenuDto;
import com.access.hub.contexts.project.domain.entity.Menu;
import com.access.hub.contexts.project.domain.repository.MenuRepository;
import com.access.hub.shared.domain.exception.BusinessRuleViolationException;
import com.access.hub.shared.domain.exception.DomainException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateMenuUseCase {
    private final MenuRepository menuRepository;
    private final OrganizationBridgeService organizationBridgeService;


    public CreateMenuUseCase(
            MenuRepository menuRepository,
            OrganizationBridgeService organizationBridgeService
    ) {
        this.menuRepository = menuRepository;
        this.organizationBridgeService = organizationBridgeService;
    }

    public Menu execute(CreateMenuDto request, String currentUserId) {
        try {
            if (menuRepository.existsMenu(request.getProjectId(), request.getUrl())) {
                throw new DomainException("Menu exist");
            }

            List<String> invalidPers = organizationBridgeService.findInvalidPermissionIds(request.getPermissionIds());
            if (!invalidPers.isEmpty()) {
                String invalidPer = String.format("Các quyền [%s] không hợp lệ", String.join(", ", invalidPers));
                throw new DomainException(invalidPer);
            }

            Menu menu = new Menu();
            menu.setDetails(request.getDetails());
            menu.setTitle(request.getTitle());
            menu.setUrl(request.getUrl());
            menu.setProjectId(request.getProjectId());
            menu.setParentId(request.getParentId());
            menu.setSortOrder(request.getSortOrder());
            menu.markAsCreated(currentUserId);

            return menuRepository.save(menu);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
