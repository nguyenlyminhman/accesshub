package com.access.hub.contexts.organization.infrastructure.persistence.dao;

import com.access.hub.contexts.organization.domain.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface DepartmentDao extends JpaRepository<Department, Integer> {
    boolean existsByDeptCode(String code);
}
