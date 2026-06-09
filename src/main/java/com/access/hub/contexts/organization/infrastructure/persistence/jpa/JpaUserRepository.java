package com.access.hub.contexts.organization.infrastructure.persistence.jpa;

import com.access.hub.contexts.organization.domain.entity.User;
import com.access.hub.contexts.organization.domain.valueobject.EmailVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface JpaUserRepository extends JpaRepository<User, Integer> {
    boolean existsByUsername(String username);
    boolean existsByEmail(EmailVO email);
}
