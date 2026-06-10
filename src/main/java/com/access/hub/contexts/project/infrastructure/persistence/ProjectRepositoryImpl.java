package com.access.hub.contexts.project.infrastructure.persistence;

import com.access.hub.contexts.project.application.dto.MenuInfoProjection;
import com.access.hub.contexts.project.domain.entity.Project;
import com.access.hub.contexts.project.domain.repository.ProjectRepository;
import com.access.hub.contexts.project.infrastructure.persistence.dao.ProjectDao;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectRepositoryImpl implements ProjectRepository {
    private final ProjectDao projectDao;

    public ProjectRepositoryImpl(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @Override
    public Project save(Project project) {
        return projectDao.save(project);
    }

    @Override
    public boolean existsByCode(String code) {
        return projectDao.existsByCode(code);
    }

    @Override
    public List<MenuInfoProjection> findRawMenusByUsernameAndProject(String username, String projectCode) {
        return projectDao.findRawMenusByUsernameAndProject(username, projectCode);
    }
}
