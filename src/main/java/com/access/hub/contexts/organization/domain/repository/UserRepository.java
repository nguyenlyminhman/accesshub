package com.access.hub.contexts.organization.domain.repository;

import com.access.hub.contexts.organization.application.dto.UserInfoProjection;
import com.access.hub.contexts.organization.domain.entity.User;
import com.access.hub.contexts.organization.domain.valueobject.EmailVO;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository {
    User save(User user);
    boolean existsByUsername(String username);
    boolean existsByEmail(EmailVO email);

    User findByUsername(String username);

    UserInfoProjection getUserDepartmentDetails(String username);

    List<String> getUserPermissions(String username, String projectCode);

    List<String> findRolesByUsernameAndProjectCode(String username, String projectCode);
}
