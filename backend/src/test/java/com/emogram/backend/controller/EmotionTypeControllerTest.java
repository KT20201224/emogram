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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class EmotionTypeControllerTest {

    @Mock
    private EmotionTypeService emotionTypeService;

    @InjectMocks
    private EmotionTypeController emotionTypeController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(emotionTypeController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("새 감정 유형 추가 - 성공")
    void createEmotionType() throws Exception {
        // Given
        EmotionTypeDto requestDto = new EmotionTypeDto("Joy", "#FFD700");
        EmotionTypeDto responseDto = new EmotionTypeDto("Joy", "#FFD700");

        when(emotionTypeService.createEmotionType(any(EmotionTypeDto.class))).thenReturn(responseDto);

        // When & Then
        mockMvc.perform(post("/api/emotion-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Joy"))
                .andExpect(jsonPath("$.color").value("#FFD700"));
    }

    @Test
    @DisplayName("모든 감정 유형 조회 - 성공")
    void getAllEmotionTypes() throws Exception {
        // Given
        EmotionTypeDto joy = new EmotionTypeDto("Joy", "#FFD700");
        EmotionTypeDto sadness = new EmotionTypeDto("Sadness", "#1F77B4");
        List<EmotionTypeDto> responseList = Arrays.asList(joy, sadness);

        when(emotionTypeService.getAllEmotionTypes()).thenReturn(responseList);

        // When & Then
        mockMvc.perform(get("/api/emotion-types")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Joy"))
                .andExpect(jsonPath("$[0].color").value("#FFD700"))
                .andExpect(jsonPath("$[1].name").value("Sadness"))
                .andExpect(jsonPath("$[1].color").value("#1F77B4"));
    }
}