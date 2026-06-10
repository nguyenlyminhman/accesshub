package com.access.hub.contexts.project.infrastructure.persistence.dao;

import com.access.hub.contexts.project.domain.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuDao extends JpaRepository<Menu, Integer> {
    Menu findByProjectIdAndUrl(int projectId, String url);
}
