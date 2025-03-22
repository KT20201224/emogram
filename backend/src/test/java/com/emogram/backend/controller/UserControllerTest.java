package com.emogram.backend.controller;

import com.emogram.backend.dto.*;
import com.emogram.backend.service.UserService;
import com.emogram.backend.security.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();

        // JWT 토큰 설정 Mock
        lenient().when(jwtTokenProvider.validateToken(anyString())).thenReturn(true);
        lenient().when(jwtTokenProvider.getEmailFromToken(anyString())).thenReturn("test@example.com");
    }

    @Test
    @DisplayName("회원가입 테스트")
    void registerUser() throws Exception {
        UserRegistrationRequest request = new UserRegistrationRequest(
                "test@example.com",
                "TestUser",
                "testPassword",
                "http://example.com/profile.png",
                "This is a test user."
        );

        UserProfileResponse response = UserProfileResponse.builder()
                .email("test@example.com")
                .nickname("TestUser")
                .profileImageUrl("http://example.com/profile.png")
                .bio("This is a test user.")
                .role("USER")
                .points(0)
                .build();

        when(userService.registerUser(any(UserRegistrationRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());  // isOk() -> isCreated() 로 변경
    }

    @Test
    @DisplayName("로그인 테스트")
    void loginUser() throws Exception {
        UserLoginRequest request = new UserLoginRequest("test@example.com", "testPassword");

        when(userService.loginUser(any(UserLoginRequest.class))).thenReturn("mockToken");

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("mockToken"));
    }

    @Test
    @DisplayName("사용자 정보 조회 테스트")
    void getUserProfileByEmail() throws Exception {
        String token = "Bearer mockToken";

        UserProfileResponse response = UserProfileResponse.builder()
                .email("test@example.com")
                .nickname("TestUser")
                .profileImageUrl("http://example.com/profile.png")
                .bio("This is a test user.")
                .role("USER")
                .points(100)
                .build();

        when(userService.getUserProfileByEmail("test@example.com")).thenReturn(response);

        mockMvc.perform(get("/api/users/profile")
                        .header("Authorization", token)
                        .param("email", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.nickname").value("TestUser"))
                .andExpect(jsonPath("$.profileImageUrl").value("http://example.com/profile.png"))
                .andExpect(jsonPath("$.bio").value("This is a test user."))
                .andExpect(jsonPath("$.role").value("USER"))
                .andExpect(jsonPath("$.points").value(100));
    }

    @Test
    @DisplayName("사용자 정보 수정 테스트")
    void updateUserProfileByEmail() throws Exception {
        UserUpdateRequest request = new UserUpdateRequest(
                "UpdatedUser",
                "http://example.com/new-profile.png",
                "Updated bio",
                "ko",
                "dark",
                true
        );

        UserProfileResponse response = UserProfileResponse.builder()
                .email("test@example.com")
                .nickname("UpdatedUser")
                .profileImageUrl("http://example.com/new-profile.png")
                .bio("Updated bio")
                .role("USER")
                .points(100)
                .build();

        when(userService.updateUserProfileByEmail(Mockito.eq("test@example.com"), any(UserUpdateRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/users/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("Authorization", "Bearer mockToken"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nickname").value("UpdatedUser"))
                .andExpect(jsonPath("$.profileImageUrl").value("http://example.com/new-profile.png"))
                .andExpect(jsonPath("$.bio").value("Updated bio"));
    }
}