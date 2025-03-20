package com.emogram.backend.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSocial {

    private String socialProvider;  // Google
    private String socialId;        // 발금된 Id;
}
