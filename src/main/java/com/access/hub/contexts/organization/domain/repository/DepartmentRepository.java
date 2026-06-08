package com.access.hub.contexts.organization.domain.repository;

import com.access.hub.contexts.organization.domain.entity.Department;

public interface DepartmentRepository {
    Department save(Department department);
    boolean existsByDeptCode(String code);
}
