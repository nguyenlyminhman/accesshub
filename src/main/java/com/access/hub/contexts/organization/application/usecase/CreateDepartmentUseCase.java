package com.access.hub.contexts.organization.application.usecase;

import com.access.hub.contexts.organization.application.dto.CreateDepartmentDto;
import com.access.hub.contexts.organization.domain.entity.Department;
import com.access.hub.contexts.organization.domain.repository.DepartmentRepository;
import com.access.hub.shared.Status;
import com.access.hub.shared.domain.exception.DomainException;
import org.springframework.stereotype.Service;

@Service
public class CreateDepartmentUseCase {
    private final DepartmentRepository departmentRepository;

    public CreateDepartmentUseCase(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department execute(CreateDepartmentDto request, String mockCurrentUserId) {

        if (departmentRepository.existsByDeptCode(request.getDeptCode())) {
            throw new DomainException("Department code exist");
        }

        Department department = new Department();
        department.setDeptCode(request.getDeptCode());
        department.setDeptName(request.getDeptName());
        department.setDetails(request.getDetails());
        department.setStatus(Status.ACTIVE);
        department.markAsCreated(mockCurrentUserId);

        return  departmentRepository.save(department);
    }
}
