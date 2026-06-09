package com.access.hub.contexts.organization.infrastructure.persistence;

import com.access.hub.contexts.organization.domain.entity.Permission;
import com.access.hub.contexts.organization.domain.repository.PersRepository;
import com.access.hub.contexts.organization.infrastructure.persistence.jpa.JpaPersRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersRepositoryImpl implements PersRepository{
    private final JpaPersRepository jpaPersRepository;

    public PersRepositoryImpl(JpaPersRepository jpaPersRepository) {
        this.jpaPersRepository = jpaPersRepository;
    }

    @Override
    public List<Permission> findAllOrderById() {
        return jpaPersRepository.findAll(Sort.by("id"));
    }
}
