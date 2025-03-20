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
public class UserInfo {

    private String nickname;
    private String email;
    private String profileImageUrl;
    private String bio;

}
