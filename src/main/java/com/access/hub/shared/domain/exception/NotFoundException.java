package com.access.hub.shared.domain.exception;

public class NotFoundException extends DomainException {
    public NotFoundException(String message) {
        super(404, message);
    }
}
