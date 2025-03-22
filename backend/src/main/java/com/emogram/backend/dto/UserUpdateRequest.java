package com.emogram.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Size;

/**
 * 사용자 정보 수정 요청 시 전달되는 데이터를 담는 DTO 클래스.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRequest {

    @Size(min = 3, max = 20, message = "Nickname must be between 3 and 20 characters.")
    private String nickname; // 수정 가능한 닉네임

    private String profileImageUrl; // 수정 가능한 프로필 이미지 URL

    private String bio; // 수정 가능한 사용자 소개글

    private String languagePreference; // 수정 가능한 언어 설정

    private String themePreference; // 수정 가능한 테마 설정

    private Boolean notificationsEnabled; // 수정 가능한 알림 설정 (true: 활성화, false: 비활성화)
}