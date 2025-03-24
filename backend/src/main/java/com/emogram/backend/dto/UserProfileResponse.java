package com.emogram.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 사용자 정보 조회 요청 시 반환되는 데이터를 담는 DTO 클래스.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileResponse {

    private Long id;                // id
    private String email;           // 사용자의 이메일
    private String nickname;         // 사용자의 닉네임
    private String profileImageUrl;  // 사용자의 프로필 이미지 URL
    private String bio;              // 사용자의 소개글
    private String role;             // 사용자의 권한 (예: USER, ADMIN)
    private int points;              // 사용자의 포인트 (기본값: 0)
}