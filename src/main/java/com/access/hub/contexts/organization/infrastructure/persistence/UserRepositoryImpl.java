package com.access.hub.contexts.organization.infrastructure.persistence;

import com.access.hub.contexts.organization.application.dto.UserInfoProjection;
import com.access.hub.contexts.organization.domain.entity.User;
import com.access.hub.contexts.organization.domain.repository.UserRepository;
import com.access.hub.contexts.organization.domain.valueobject.EmailVO;
import com.access.hub.contexts.organization.infrastructure.persistence.dao.UserDao;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepositoryImpl implements UserRepository {

    private final UserDao userDao;

    public UserRepositoryImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User save(User user) {
        return userDao.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userDao.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(EmailVO email) {
        return userDao.existsByEmail(email);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public UserInfoProjection getUserDepartmentDetails(String username) {
        return userDao.getUserDepartmentDetails(username);
    }

    @Override
    public List<String> getUserPermissions(String username, String projectCode) {
        return null;
    }

    @Override
    public List<String> findRolesByUsernameAndProjectCode(String username, String projectCode) {
        return userDao.findRolesByUsernameAndProjectCode(username, projectCode);
    }


}
