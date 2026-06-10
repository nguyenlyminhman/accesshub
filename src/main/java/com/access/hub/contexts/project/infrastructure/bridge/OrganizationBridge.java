package com.access.hub.contexts.project.infrastructure.bridge;

import com.access.hub.contexts.organization.application.port.OrganizationBridgeService;
import com.access.hub.contexts.organization.domain.repository.PersRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrganizationBridge implements OrganizationBridgeService {

    private final PersRepository persRepository;

    public OrganizationBridge(PersRepository persRepository) {
        this.persRepository = persRepository;
    }


    @Override
    public List<String> findInvalidPermissionIds(List<Integer> permissionIds) {
        return persRepository.findAllByIds(permissionIds);
    }
}
