package com.access.hub.shared.domain.exception;

public class UnauthorizedException  extends DomainException {
    public UnauthorizedException(String message) {
        super(401, message);
    }
}
