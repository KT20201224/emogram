package com.emogram.backend.controller;

import com.emogram.backend.dto.EmotionTypeDto;
import com.emogram.backend.service.EmotionTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

@ExtendWith(MockitoExtension.class)
class EmotionTypeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmotionTypeService emotionTypeService;

    @InjectMocks
    private EmotionTypeController emotionTypeController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(emotionTypeController).build();
    }

    @Test
    @DisplayName("새로운 감정 유형을 추가하는 API 테스트")
    void createEmotionTypeTest() throws Exception {
        // Given
        EmotionTypeDto requestDto = new EmotionTypeDto("기쁨", "#FFFF00");
        EmotionTypeDto responseDto = new EmotionTypeDto("기쁨", "#FFFF00");

        when(emotionTypeService.createEmotionType(any(EmotionTypeDto.class))).thenReturn(responseDto);

        // When & Then
        mockMvc.perform(post("/api/emotion-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\":\"기쁨\", \"color\":\"#FFFF00\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("기쁨"))
                .andExpect(jsonPath("$.color").value("#FFFF00"));
    }

    @Test
    @DisplayName("모든 감정 유형을 조회하는 API 테스트")
    void getAllEmotionTypesTest() throws Exception {
        // Given
        List<EmotionTypeDto> emotionTypes = List.of(
                new EmotionTypeDto("기쁨", "#FFFF00"),
                new EmotionTypeDto("슬픔", "#0000FF")
        );

        when(emotionTypeService.getAllEmotionTypes()).thenReturn(emotionTypes);

        // When & Then
        mockMvc.perform(get("/api/emotion-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("기쁨"))
                .andExpect(jsonPath("$[0].color").value("#FFFF00"))
                .andExpect(jsonPath("$[1].type").value("슬픔"))
                .andExpect(jsonPath("$[1].color").value("#0000FF"));
    }
}