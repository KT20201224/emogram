package com.emogram.backend.controller;

import com.emogram.backend.dto.*;
import com.emogram.backend.security.JwtTokenProvider;
import com.emogram.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * 사용자 관련 API를 처리하는 컨트롤러 클래스
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * 회원가입 API (POST /api/users/register)
     */
    @PostMapping("/register")
    public ResponseEntity<UserProfileResponse> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        UserProfileResponse response = userService.registerUser(request);  // UserService를 통해 회원가입 처리
        return ResponseEntity.status(HttpStatus.CREATED).body(response);  // 201(CREATED) 상태 코드로 응답
    }

    /**
     * 로그인 API (POST /api/users/login)
     */
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody UserLoginRequest request) {
        String token = userService.loginUser(request);
        return ResponseEntity.ok(token);
    }

    /**
     * 사용자 정보 조회 API (GET /api/users/profile)
     */
    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getUserProfile(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token
    ) {
        String email = jwtTokenProvider.getEmailFromToken(token.replace("Bearer ", ""));  // JWT 토큰에서 이메일 추출
        UserProfileResponse response = userService.getUserProfileByEmail(email);  // UserService에서 사용자 정보 조회
        return ResponseEntity.ok(response);  // 조회된 사용자 정보 반환
    }

    /**
     * 사용자 정보 수정 API (PUT /api/users/profile)
     */
    @PutMapping("/profile")
    public ResponseEntity<UserProfileResponse> updateUserProfile(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @Valid @RequestBody UserUpdateRequest request
    ) {
        String email = jwtTokenProvider.getEmailFromToken(token.replace("Bearer ", ""));  // JWT 토큰에서 이메일 추출
        UserProfileResponse response = userService.updateUserProfileByEmail(email, request);  // 사용자 정보 수정
        return ResponseEntity.ok(response);  // 수정된 사용자 정보 반환
    }
}