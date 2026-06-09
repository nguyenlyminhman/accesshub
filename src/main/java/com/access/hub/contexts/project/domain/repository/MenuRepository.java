package com.access.hub.contexts.project.domain.repository;

import com.access.hub.contexts.project.domain.entity.Menu;

public interface MenuRepository {
    Menu save (Menu menu);
    boolean existsMenu(int projectId, String url);
}
