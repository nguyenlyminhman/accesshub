package com.access.hub.contexts.project.infrastructure.persistence;

import com.access.hub.contexts.project.domain.entity.Menu;
import com.access.hub.contexts.project.domain.repository.MenuRepository;
import com.access.hub.contexts.project.infrastructure.persistence.dao.MenuDao;
import com.access.hub.shared.domain.exception.DomainException;
import org.springframework.stereotype.Component;

@Component
public class MenuRepositoryImpl implements MenuRepository {

    private final MenuDao menuDao;

    public MenuRepositoryImpl(MenuDao menuDao) {
        this.menuDao = menuDao;
    }

    @Override
    public Menu save(Menu menu) {
        return menuDao.save(menu);
    }

    @Override
    public boolean existsMenu(int projectId, String url) {
        Menu menu =  menuDao.findByProjectIdAndUrl(projectId, url);
        if (menu != null) {
            return true;
        }
        return false;
    }
}
