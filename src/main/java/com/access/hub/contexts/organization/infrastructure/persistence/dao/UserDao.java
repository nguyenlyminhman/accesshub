package com.access.hub.contexts.organization.infrastructure.persistence.dao;

import com.access.hub.contexts.organization.application.dto.UserInfoProjection;
import com.access.hub.contexts.organization.domain.entity.User;
import com.access.hub.contexts.organization.domain.valueobject.EmailVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserDao extends JpaRepository<User, Integer> {
    boolean existsByUsername(String username);
    boolean existsByEmail(EmailVO email);

    User findByUsername(String username);

    @Query("SELECT new com.access.hub.contexts.organization.application.dto.UserInfoProjection(" +
            " u.username, u.email, d.deptCode, d.deptName, d.details ) " +
            "FROM User u JOIN Department d on d.id = u.deptId " +
            "AND u.username = :username " +
            "AND u.status = 'ACTIVE' " +
            "AND u.deletedAt IS NULL " +
            "AND d.status = 'ACTIVE' " +
            "AND d.deletedAt IS NULL ")
    UserInfoProjection getUserDepartmentDetails(String username);

    @Query(value = """
        SELECT DISTINCT r.role_code AS roleCode
        FROM users u
        JOIN group_roles gr ON u.id = gr.user_id
        JOIN roles r ON gr.role_id = r.id
        JOIN projects pr ON r.project_id = pr.id
        WHERE u.username = :username
          AND pr.code = :projectCode
          AND u.deleted_at IS NULL
          AND r.deleted_at IS NULL
          AND pr.deleted_at IS NULL
        ORDER BY r.role_code ASC
    """, nativeQuery = true)
    List<String> findRolesByUsernameAndProjectCode(@Param("username") String username, @Param("projectCode") String projectCode);
}
