package com.access.hub.contexts.organization.domain.valueobject;

import com.access.hub.shared.domain.exception.BusinessRuleViolationException;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmailVO {

    @Column(name = "email")
    private String value;

    public EmailVO(String _email) {
        if (_email == null || _email.trim().isEmpty()) {
            throw new BusinessRuleViolationException("Email không được để trống!");
        }

        if (!_email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new BusinessRuleViolationException("Định dạng email không hợp lệ!");
        }

        this.value = _email.toLowerCase().trim();
    }
}
