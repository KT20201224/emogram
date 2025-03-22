package com.emogram.backend.service;

import com.emogram.backend.dto.UserProfileResponse;
import com.emogram.backend.dto.UserUpdateRequest;
import com.emogram.backend.entity.*;
import com.emogram.backend.repository.UserRepository;
import com.emogram.backend.dto.UserRegistrationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        UserInfo userInfo = UserInfo.builder()
                .email("test@example.com")
                .nickname("TestUser")
                .profileImageUrl("http://example.com/profile.png")
                .bio("This is a test user.")
                .build();

        UserSecurity userSecurity = UserSecurity.builder()
                .password("encodedPassword")
                .lastLoginAt(LocalDateTime.now())
                .isActive(true)
                .failedLoginAttempts(0)
                .role("USER")
                .build();

        UserSettings userSettings = UserSettings.builder()
                .languagePreference("en")
                .themePreference("dark")
                .notificationsEnabled(true)
                .build();

        UserSocial userSocial = UserSocial.builder()
                .socialProvider("Google")
                .socialId("google-12345")
                .build();

        UserExtra userExtra = UserExtra.builder()
                .points(100)
                .build();

        testUser = new User(userInfo, userSecurity, userSettings, userSocial, userExtra);
    }

    @Test
    @DisplayName("회원가입 테스트")
    void registerUser() {
        UserRegistrationRequest request = new UserRegistrationRequest(
                "test@example.com",
                "TestUser",
                "encodedPassword",
                "http://example.com/profile.png",
                "This is a test user."
        );

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");  // 🔑 PasswordEncoder Mock 동작 정의
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        UserProfileResponse savedUser = userService.registerUser(request);

        assertNotNull(savedUser);
        assertEquals("test@example.com", savedUser.getEmail());
        assertEquals("TestUser", savedUser.getNickname());
        assertEquals("http://example.com/profile.png", savedUser.getProfileImageUrl());
        assertEquals("This is a test user.", savedUser.getBio());
        assertEquals("USER", savedUser.getRole());
        assertEquals(0, savedUser.getPoints());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("이메일로 사용자 조회 테스트")
    void getUserByEmail() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));

        UserProfileResponse foundUser = userService.getUserProfileByEmail("test@example.com");

        assertNotNull(foundUser);
        assertEquals("test@example.com", foundUser.getEmail());
        assertEquals("TestUser", foundUser.getNickname());
        assertEquals("http://example.com/profile.png", foundUser.getProfileImageUrl());
        assertEquals("This is a test user.", foundUser.getBio());
        assertEquals("USER", foundUser.getRole());
        assertEquals(100, foundUser.getPoints());
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    @DisplayName("사용자 정보 수정 테스트")
    void updateUserProfile() {
        UserUpdateRequest request = new UserUpdateRequest(
                "UpdatedNickname",
                "http://example.com/new-profile.png",
                "Updated bio",
                "ko",
                "dark",
                true
        );

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));

        UserProfileResponse updatedUser = userService.updateUserProfileByEmail("test@example.com", request);

        assertEquals("UpdatedNickname", updatedUser.getNickname());
        assertEquals("http://example.com/new-profile.png", updatedUser.getProfileImageUrl());
        assertEquals("Updated bio", updatedUser.getBio());
        assertEquals("USER", updatedUser.getRole());
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    @DisplayName("사용자 로그인 시 마지막 로그인 시간 업데이트 테스트")
    void updateLastLogin() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));

        userService.updateLastLogin("test@example.com");

        assertNotNull(testUser.getUserSecurity().getLastLoginAt());
        verify(userRepository, times(1)).save(testUser);
    }
}