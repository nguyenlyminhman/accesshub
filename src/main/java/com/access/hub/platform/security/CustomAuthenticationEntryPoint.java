package com.access.hub.platform.security;

import com.access.hub.shared.application.dto.ResponseObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public CustomAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        Map<String, Object> body = new HashMap<>();
        body.clear();
        body.put("path", request.getServletPath());
        body.put("timestamp", Instant.now().toString());

        ResponseObject responseObject = new ResponseObject();
        responseObject.setSuccess(false);
        responseObject.setCode(HttpStatus.UNAUTHORIZED.value());
        responseObject.setMessage("Access denied! Unauthorized");
        responseObject.setData(body);

        objectMapper.writeValue(response.getOutputStream(), responseObject);
    }
}