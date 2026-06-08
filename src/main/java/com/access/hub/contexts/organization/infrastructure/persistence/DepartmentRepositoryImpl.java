package com.access.hub.contexts.organization.infrastructure.persistence;

import com.access.hub.contexts.organization.domain.entity.Department;
import com.access.hub.contexts.organization.domain.repository.DepartmentRepository;
import com.access.hub.contexts.organization.infrastructure.persistence.jpa.JpaDepartmentRepository;
import org.springframework.stereotype.Component;

@Component
public class DepartmentRepositoryImpl implements DepartmentRepository {

    private final JpaDepartmentRepository departmentRepository;

    public DepartmentRepositoryImpl(JpaDepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Department save(Department department) {
        return this.departmentRepository.save(department);
    }

    @Override
    public boolean existsByDeptCode(String code) {
        return this.departmentRepository.existsByDeptCode(code);
    }
}
