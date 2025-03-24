package com.emogram.backend.service;

import com.emogram.backend.dto.*;
import com.emogram.backend.entity.*;
import com.emogram.backend.repository.UserRepository;
import com.emogram.backend.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;  // 수정: BCryptPasswordEncoder → PasswordEncoder
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;  // 수정된 부분
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * 사용자 등록 (회원가입)
     */
    public UserProfileResponse registerUser(UserRegistrationRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // UserInfo 생성
        UserInfo userInfo = new UserInfo(
                request.getEmail(),
                request.getNickname(),
                request.getProfileImageUrl(),
                request.getBio()
        );

        // UserSecurity 생성
        UserSecurity userSecurity = new UserSecurity(
                encodedPassword,
                null,
                true,
                0,
                "USER" // 기본 사용자 권한 설정
        );

        // UserSettings 생성
        UserSettings userSettings = new UserSettings(
                "en", // 기본 언어 설정
                "light", // 기본 테마 설정
                true // 알림 활성화
        );

        //UserSocial 생성
        UserSocial userSocial = new UserSocial(
                null,
                null
        );

        // UserExtra 생성 (포인트 초기값 설정)
        UserExtra userExtra = new UserExtra(0);


        // User 객체 생성
        User user = new User(userInfo, userSecurity, userSettings, userSocial, userExtra);

        // User 저장
        userRepository.save(user);

        // 저장된 사용자 정보를 반환
        return convertToProfileResponse(user);
    }

    /**
     * 사용자 로그인
     */
    public String loginUser(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

        // 비밀번호 확인
        if (!passwordEncoder.matches(request.getPassword(), user.getUserSecurity().getPassword())) {
            throw new IllegalArgumentException("Invalid email or password.");
        }

        // 토큰 발급 (JWT)
        return jwtTokenProvider.createToken(user.getUserInfo().getEmail(), user.getUserSecurity().getRole());
    }

    /**
     * 사용자 정보 조회
     */
    public UserProfileResponse getUserProfileByEmail(String email) {
        // 이메일로 사용자 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        // 조회된 사용자 정보를 응답 DTO로 변환하여 반환
        return convertToProfileResponse(user);
    }

    /**
     * 사용자 정보 수정
     */
    public UserProfileResponse updateUserProfileByEmail(String email, UserUpdateRequest request) {
        // 이메일로 사용자 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        // 닉네임 수정 (사용자가 입력한 경우에만)
        if (request.getNickname() != null) {
            user.getUserInfo().setNickname(request.getNickname());
        }

        // 프로필 이미지 URL 수정 (사용자가 입력한 경우에만)
        if (request.getProfileImageUrl() != null) {
            user.getUserInfo().setProfileImageUrl(request.getProfileImageUrl());
        }

        // 사용자 소개글 수정 (사용자가 입력한 경우에만)
        if (request.getBio() != null) {
            user.getUserInfo().setBio(request.getBio());
        }

        // 언어 설정 수정
        if (request.getLanguagePreference() != null) {
            user.getUserSettings().setLanguagePreference(request.getLanguagePreference());
        }

        // 테마 설정 수정
        if (request.getThemePreference() != null) {
            user.getUserSettings().setThemePreference(request.getThemePreference());
        }

        // 알림 설정 수정 (null 이 아닐 때만 업데이트)
        if (request.getNotificationsEnabled() != null) {  // 수정: isNotificationsEnabled() -> getNotificationsEnabled()
            user.getUserSettings().setNotificationsEnabled(request.getNotificationsEnabled());
        }

        userRepository.save(user); // 수정된 사용자 정보 저장

        // 수정된 사용자 정보를 응답 DTO로 변환하여 반환
        return convertToProfileResponse(user);
    }

    /**
     * 사용자 정보를 UserProfileResponse로 변환
     */
    private UserProfileResponse convertToProfileResponse(User user) {
        // User 엔티티에서 정보를 추출하여 DTO로 변환
        return UserProfileResponse.builder()
                .email(user.getUserInfo().getEmail())
                .nickname(user.getUserInfo().getNickname())
                .profileImageUrl(user.getUserInfo().getProfileImageUrl())
                .bio(user.getUserInfo().getBio())
                .role(user.getUserSecurity().getRole())
                .points(user.getUserExtra().getPoints())
                .build();
    }

    /**
     * 사용자 로그인 시 마지막 로그인 시간 업데이트
     */
    @Transactional
    public void updateLastLogin(String email) {
        // 이메일로 사용자 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        // 마지막 로그인 시간 업데이트
        user.getUserSecurity().setLastLoginAt(LocalDateTime.now());

        // 업데이트된 사용자 정보 저장
        userRepository.save(user);
    }

    public Long getUserIdByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return user.getId();  // UserInfo의 ID 반환
    }
}