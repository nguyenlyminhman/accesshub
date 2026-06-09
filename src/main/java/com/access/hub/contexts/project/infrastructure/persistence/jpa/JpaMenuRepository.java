package com.access.hub.contexts.project.infrastructure.persistence.jpa;

import com.access.hub.contexts.project.domain.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaMenuRepository extends JpaRepository<Menu, Integer> {
    Menu findByProjectIdAndUrl(int projectId, String url);
}
