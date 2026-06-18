package com.access.hub.contexts.organization.application.usecase;

import com.access.hub.contexts.organization.application.dto.CreateUserDto;
import com.access.hub.contexts.organization.domain.entity.User;
import com.access.hub.contexts.organization.domain.repository.UserRepository;
import com.access.hub.shared.Status;
import com.access.hub.shared.domain.exception.DomainException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateUserUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User execute(CreateUserDto dto, String username) {
        try {
            if (userRepository.existsByUsername(dto.getUsername())) {
                throw new DomainException("User exist");
            }

            if (userRepository.existsByEmail(dto.getEmail())) {
                throw new DomainException("Email exist");
            }

            String passwordHash = passwordEncoder.encode(dto.getPassword());
            User user = new User();
            user.setDeptId(dto.getDeptId());
            user.setEmail(dto.getEmail());
            user.setUsername(dto.getUsername());
            user.setPassword(passwordHash);
            user.setStatus(Status.ACTIVE);
            user.markAsCreated(username);

            userRepository.save(user);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
