package com.access.hub.contexts.organization.domain.repository;

import com.access.hub.contexts.organization.domain.entity.User;
import com.access.hub.contexts.organization.domain.valueobject.EmailVO;

public interface UserRepository {
    User save(User user);
    boolean existsByUsername(String username);
    boolean existsByEmail(EmailVO email);
}
