package com.access.hub.contexts.project.infrastructure.persistence.dao;

import com.access.hub.contexts.project.application.dto.MenuInfoProjection;
import com.access.hub.contexts.project.domain.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectDao extends JpaRepository<Project, Integer> {
    boolean existsByPrjCode(String code);

    @Query(value = """
        SELECT DISTINCT 
            m.id AS menuId, 
            m.parent_id AS parentId,  
            m.title AS menuTitle, 
            m.url AS menuUrl, 
            m.sort_order AS sortOrder
        FROM users u
        JOIN group_roles gr ON u.id = gr.user_id
        JOIN roles r ON gr.role_id = r.id
        JOIN projects p ON r.project_id = p.id
        JOIN role_menu_permissions rmp ON r.id = rmp.role_id
        JOIN menus m ON rmp.menu_id = m.id AND m.project_id = p.id
        WHERE u.username = :username 
          AND p.code = :projectCode
          AND u.deleted_at IS NULL
          AND r.deleted_at IS NULL
          AND p.deleted_at IS NULL
          AND m.deleted_at IS NULL
        ORDER BY m.parent_id ASC, m.sort_order ASC
    """, nativeQuery = true)
    List<MenuInfoProjection> findRawMenusByUsernameAndProject(@Param("username") String username, @Param("projectCode") String projectCode);
}
