package com.emogram.backend.controller;

import com.emogram.backend.dto.MemoryOrbRequest;
import com.emogram.backend.dto.MemoryOrbResponse;
import com.emogram.backend.service.MemoryOrbService;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class MemoryOrbControllerTest {

    @Mock
    private MemoryOrbService memoryOrbService;

    @InjectMocks
    private MemoryOrbController memoryOrbController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(memoryOrbController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("MemoryOrb 생성 테스트")
    void createMemoryOrb() throws Exception {
        MemoryOrbRequest request = new MemoryOrbRequest(1L, 1L, "행복한 하루였다.");
        MemoryOrbResponse response = MemoryOrbResponse.builder()
                .orbId("12345")
                .emotionTypeName("Joy")
                .color("#FFD700")
                .description("행복한 하루였다.")
                .timestamp(LocalDateTime.now())
                .build();

        when(memoryOrbService.createMemoryOrb(any(MemoryOrbRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/memory-orbs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("모든 MemoryOrb 조회 테스트")
    void getAllMemoryOrbs() throws Exception {
        MemoryOrbResponse response = MemoryOrbResponse.builder()
                .orbId("12345")
                .emotionTypeName("Joy")
                .color("#FFD700")
                .description("행복한 하루였다.")
                .timestamp(LocalDateTime.now())
                .build();

        when(memoryOrbService.getAllMemoryOrbs()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/memory-orbs/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].emotionTypeName").value("Joy"));
    }

    @Test
    @DisplayName("특정 사용자 MemoryOrb 조회 테스트")
    void getMemoryOrbsByUserId() throws Exception {
        MemoryOrbResponse response = MemoryOrbResponse.builder()
                .orbId("12345")
                .emotionTypeName("Joy")
                .color("#FFD700")
                .description("행복한 하루였다.")
                .timestamp(LocalDateTime.now())
                .build();

        when(memoryOrbService.getMemoryOrbsByUserId(anyLong())).thenReturn(List.of(response));

        mockMvc.perform(get("/api/memory-orbs")
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].emotionTypeName").value("Joy"));
    }

    @Test
    @DisplayName("MemoryOrb 수정 테스트")
    void updateMemoryOrb() throws Exception {
        MemoryOrbRequest request = new MemoryOrbRequest(1L, 1L, "수정된 감정 기록");

        MemoryOrbResponse response = MemoryOrbResponse.builder()
                .orbId("12345")
                .emotionTypeName("Joy")
                .color("#FFD700")
                .description("수정된 감정 기록")
                .timestamp(LocalDateTime.now())
                .build();

        when(memoryOrbService.updateMemoryOrb(anyString(), any(MemoryOrbRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/memory-orbs/12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("수정된 감정 기록"));
    }

    @Test
    @DisplayName("MemoryOrb 삭제 테스트")
    void deleteMemoryOrb() throws Exception {
        Mockito.doNothing().when(memoryOrbService).deleteMemoryOrb(anyString());

        mockMvc.perform(delete("/api/memory-orbs/12345"))
                .andExpect(status().isNoContent());
    }
}