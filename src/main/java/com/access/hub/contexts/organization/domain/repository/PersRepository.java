package com.access.hub.contexts.organization.domain.repository;

import com.access.hub.contexts.organization.domain.entity.Permission;

import java.util.List;

public interface PersRepository {
    List<Permission> findAllOrderById();

    List<String> findAllByIds(List<Integer> permissionIds);
}
