package com.emogram.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 회원가입 요청 시 전달되는 데이터를 담는 DTO 클래스.
 */
@Data // Getter, Setter, toString, EqualsAndHashCode 등을 자동으로 생성
@NoArgsConstructor // 파라미터가 없는 기본 생성자 생성
@AllArgsConstructor // 모든 필드를 받는 생성자 생성
@Builder // 빌더 패턴으로 객체를 생성할 수 있게 함
public class UserRegistrationRequest {

    @NotBlank(message = "Email is required.") // 값이 비어 있으면 안 됨 (공백도 허용하지 않음)
    @Email(message = "Invalid email format.") // 이메일 형식 검증
    private String email;

    @NotBlank(message = "Nickname is required.") // 닉네임은 반드시 필요함
    @Size(min = 3, max = 20, message = "Nickname must be between 3 and 20 characters.") // 닉네임 길이 제한
    private String nickname;

    @NotBlank(message = "Password is required.") // 비밀번호는 반드시 필요함
    @Size(min = 8, message = "Password must be at least 8 characters long.") // 비밀번호 최소 길이 설정
    private String password;

    private String profileImageUrl; // 프로필 이미지 URL (선택 사항)
    private String bio; // 사용자 소개글 (선택 사항)
}