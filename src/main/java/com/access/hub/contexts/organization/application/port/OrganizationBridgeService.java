package com.access.hub.contexts.organization.application.port;

import java.util.List;

public interface OrganizationBridgeService {
    List<String> findInvalidPermissionIds(List<Integer> permissionIds);
}
