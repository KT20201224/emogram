package com.emogram.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private UserInfo userInfo;

    @Embedded
    private  UserSecurity userSecurity;

    @Embedded
    private UserSettings userSettings;

    @Embedded
    private UserSocial userSocial;

    @Embedded
    private UserExtra userExtra;
}
