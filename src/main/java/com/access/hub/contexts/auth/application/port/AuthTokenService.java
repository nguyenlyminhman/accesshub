package com.access.hub.contexts.auth.application.port;

public interface AuthTokenService {
    String createToken(String username, String email, String deptCode, String deptName, String projectCode);
}
