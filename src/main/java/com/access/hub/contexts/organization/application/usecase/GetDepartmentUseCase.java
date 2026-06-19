package com.access.hub.contexts.organization.application.usecase;

import com.access.hub.contexts.organization.domain.entity.Department;
import com.access.hub.contexts.organization.domain.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetDepartmentUseCase {
    private final DepartmentRepository departmentRepository;

    public GetDepartmentUseCase(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> execute() {
        return  departmentRepository.getAllDepartment();
    }
}
