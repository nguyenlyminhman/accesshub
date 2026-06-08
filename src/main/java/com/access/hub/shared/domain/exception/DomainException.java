package com.access.hub.shared.domain.exception;

import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {
    private final int statusCode;

    public DomainException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public DomainException(String message) {
        super(message);
        this.statusCode = 400;
    }
}
