package com.access.hub.contexts.auth.infrastructure.sercurity;

import com.access.hub.contexts.auth.application.port.AuthTokenService;
import org.springframework.stereotype.Service;

@Service
public class AuthTokenServiceImpl implements AuthTokenService {
    private final JwtProvider jwtProvider;

    public AuthTokenServiceImpl(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public String createToken(String username, String email, String deptCode, String deptName, String projectCode) {
        return jwtProvider.generateAccessToken(username, email, deptCode, deptName, projectCode);
    }
}
