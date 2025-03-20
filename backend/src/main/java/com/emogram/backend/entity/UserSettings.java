package com.emogram.backend.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSettings {

    private String languagePreference;      // 언어
    private String themePreference;         // 테마 설정 (라이트,다크)
    private boolean notificationsEnabled;   // 알림 수신 여부
}
