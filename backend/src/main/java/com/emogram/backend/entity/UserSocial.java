package com.emogram.backend.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSocial {

    private String socialProvider;  // Google
    private String socialId;        // 발금된 Id;
}
