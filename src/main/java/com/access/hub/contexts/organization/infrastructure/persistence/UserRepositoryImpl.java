package com.access.hub.contexts.organization.infrastructure.persistence;

import com.access.hub.contexts.organization.domain.entity.User;
import com.access.hub.contexts.organization.domain.repository.UserRepository;
import com.access.hub.contexts.organization.domain.valueobject.EmailVO;
import com.access.hub.contexts.organization.infrastructure.persistence.jpa.JpaUserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository userRepository;

    public UserRepositoryImpl(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(EmailVO email) {
        return userRepository.existsByEmail(email);
    }
}
