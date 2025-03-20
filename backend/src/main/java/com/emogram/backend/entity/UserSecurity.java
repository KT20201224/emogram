package com.emogram.backend.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSecurity {

    private String password;
    private LocalDateTime lastLoginAt;
    private boolean isActive;
    private int failedLoginAttempts;
    private String role;
}
