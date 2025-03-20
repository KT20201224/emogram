package com.emogram.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo {

    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String nickname;

    private String profileImageUrl;
    private String bio;


}
