package com.emogram.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * 로그인 요청 시 전달되는 데이터를 담는 DTO 클래스.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginRequest {

    @NotBlank(message = "Email is required.") // 이메일은 반드시 필요함
    @Email(message = "Invalid email format.") // 이메일 형식 확인
    private String email;

    @NotBlank(message = "Password is required.") // 비밀번호는 반드시 필요함
    private String password;
}