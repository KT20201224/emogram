package com.emogram.backend.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
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
