package com.access.hub.contexts.project.infrastructure.persistence;

import com.access.hub.contexts.project.domain.entity.Menu;
import com.access.hub.contexts.project.domain.repository.MenuRepository;
import com.access.hub.contexts.project.infrastructure.persistence.jpa.JpaMenuRepository;
import org.springframework.stereotype.Component;

@Component
public class MenuRepositoryImpl implements MenuRepository {

    private final JpaMenuRepository jpaMenuRepository;

    public MenuRepositoryImpl(JpaMenuRepository jpaMenuRepository) {
        this.jpaMenuRepository = jpaMenuRepository;
    }

    @Override
    public Menu save(Menu menu) {
        return jpaMenuRepository.save(menu);
    }

    @Override
    public boolean existsMenu(int projectId, String url) {
        Menu menu =  jpaMenuRepository.findByProjectIdAndUrl(projectId, url);
        if (menu != null) {
            return true;
        }
        return false;
    }
}
