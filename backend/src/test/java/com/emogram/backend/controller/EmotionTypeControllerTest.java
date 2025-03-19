package com.emogram.backend.controller;

import com.emogram.backend.dto.EmotionTypeDto;
import com.emogram.backend.service.EmotionTypeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class) // ✅ MockBean 대신 사용
class EmotionTypeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmotionTypeService emotionTypeService;

    @InjectMocks
    private EmotionTypeController emotionTypeController; // ✅ 실제 Controller를 Inject

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(emotionTypeController).build(); // ✅ MockMvc 수동 설정
    }

    @Test
    @DisplayName("새로운 감정 유형 추가 API 테스트")
    void createEmotionTypeTest() throws Exception {
        //given
        EmotionTypeDto requestDto = new EmotionTypeDto("행복", "#FFD700");
        EmotionTypeDto responseDto = new EmotionTypeDto("행복", "#FFD700");

        //when
        when(emotionTypeService.createEmotionType(any(EmotionTypeDto.class))).thenReturn(responseDto);

        //then
        mockMvc.perform(post("/api/emotions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.type").value("행복"))
                .andExpect(jsonPath("$.color").value("#FFD700"));
    }

    @Test
    @DisplayName("모든 감정 유형 조회 API 테스트")
    void getAllEmotionTypesTest() throws Exception {
        //given
        List<EmotionTypeDto> emotionList = List.of(
                new EmotionTypeDto("기쁨", "#FFFF00"),
                new EmotionTypeDto("슬픔", "#0000FF")
        );

        //when
        when(emotionTypeService.getAllEmotionTypes()).thenReturn(emotionList);

        //then
        mockMvc.perform(get("/api/emotions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("기쁨"))
                .andExpect(jsonPath("$[1].type").value("슬픔"));
    }
}