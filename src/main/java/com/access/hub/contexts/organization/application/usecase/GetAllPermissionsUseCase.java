package com.access.hub.contexts.organization.application.usecase;

import com.access.hub.contexts.organization.domain.entity.Permission;
import com.access.hub.contexts.organization.domain.repository.PersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllPermissionsUseCase {
    private final PersRepository persRepository;

    public  GetAllPermissionsUseCase (PersRepository persRepository) {
        this.persRepository = persRepository;
    }

    public List<Permission> execute() {
        return persRepository.findAllOrderById();
    }


}
