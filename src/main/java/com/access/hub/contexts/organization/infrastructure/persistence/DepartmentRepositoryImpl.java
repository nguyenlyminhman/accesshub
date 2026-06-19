package com.access.hub.contexts.organization.infrastructure.persistence;

import com.access.hub.contexts.organization.domain.entity.Department;
import com.access.hub.contexts.organization.domain.repository.DepartmentRepository;
import com.access.hub.contexts.organization.infrastructure.persistence.dao.DepartmentDao;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DepartmentRepositoryImpl implements DepartmentRepository {

    private final DepartmentDao departmentRepository;

    public DepartmentRepositoryImpl(DepartmentDao departmentRepository) {
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

    @Override
    public List<Department> getAllDepartment() {
        return this.departmentRepository.findAll();
    }
}
