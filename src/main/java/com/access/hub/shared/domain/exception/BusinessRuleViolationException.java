package com.access.hub.shared.domain.exception;

public class BusinessRuleViolationException extends DomainException {
    public BusinessRuleViolationException(String message) {
        super(400, message);
    }
}